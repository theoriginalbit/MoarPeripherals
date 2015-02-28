--[[   MoarPeripherals  ]]--
--[[     StarCaster     ]]--
--[[       by Dog       ]]--
--[[        aka         ]]--
--[[   HydrantHunter    ]]--
--[[        and         ]]--
--[[   theoriginalbit   ]]--
--[[        aka         ]]--
--[[   TheOriginalBIT   ]]--
--[[ pastebin: mwdc6bK9 ]]--
local scVer = "1.0.01"
--# Custom read, formatTime, newButton, 
  --# newNumberPicker, pickerChanged, calculateMinMax, 
  --# inventory filtering, and dyeToColor functions, 
  --# and LOTS of tutoring, courtesy of theoriginalbit
local tArgs = { ... }
local termX, termY = term.getSize()
local launcher, launchTimer, screenTimer
local effectsLookup = { "No Effect", "Twinkle", "Trail" }
local shapes = { "Small Ball", "Large Ball", "Star", "Creeper Head", "Burst", "None" }
local effects = { "No Effect", "Twinkle", "Trail", "Twinkle & Trail" }
local runMode, operatingMode = "standard", "main"
local showType, showPacing, finaleMode, goFinale = false, false, false, false
local gettingHelp, wereDoneHere, popUp, badInventory, badWolf = false, false, false, false, false
local starError, buildError, readyError, launchError, bPressed = "none", "none", "none", "none", "none"
local minStars, maxStars, sessionNumber, currentRocket = 1, 7, 0, 1
local numRockets, minHeight, maxHeight, minColors, maxColors = 25, 1, 3, 1, 8
local shapeChance, effectChance, starsMade, abortCounter = 30, 30, 0, 0
local TIMER_GRANULARITY, timerTicker, waitTime, pageNum, numPages = 0.10, 0, 0, 1, 1
local readyRocketID, launchHeight, starColors, starShape, starEffect, numStars
local rocketParts, starIDTable, rocketIDTable, errorLog = { }, { }, { }, { }
local colorsList, effectsList, shapesList, guiElements = { }, { }, { }, { }
local colorsAvailable, effectsAvailable, shapesAvailable = { }, { }, { }
--# reference table for what we want, and what table it maps to
local inventoryCrossRef = {
                            { "item.paper", rocketParts };
                            { "item.sulphur", rocketParts };
                            { "item.dyepowder", colorsAvailable };
                            { "item.yellowdust", effectsAvailable };
                            { "item.diamond", effectsAvailable };
                            { "item.goldnugget", shapesAvailable };
                            { "item.fireball", shapesAvailable };
                            { "item.feather", shapesAvailable };
                            { "item.skull", shapesAvailable };
                          }
--# reference table for updating our values and variables as selected via picker
local valueUpdate = {
                      { "minHeight", function() return minHeight end, function(value) minHeight = value end };
                      { "maxHeight", function() return maxHeight end, function(value) maxHeight = value end };
                      { "minStars", function() return minStars end, function(value) minStars = value end };
                      { "maxStars", function() return maxStars end, function(value) maxStars = value end };
                      { "minColors", function() return minColors end, function(value) minColors = value end };
                      { "maxColors", function() return maxColors end, function(value) maxColors = value end };
                    }
--# lookup table for shape/effect selection and assignment
local ingredientValues = {
                           { "item.fireball", 1 };
                           { "item.goldnugget", 2 };
                           { "item.skull.skeleton", 3 };
                           { "item.skull.wither", 3 };
                           { "item.skull.zombie", 3 };
                           { "item.skull.creeper", 3 };
                           { "item.skull.char", 3 };
                           { "item.feather", 4 };
                           { "item.yellowdust", 1 };
                           { "item.diamond", 2 };
                         }

local function clearScreen(bgColor)
  term.setBackgroundColor(bgColor and bgColor or colors.black)
  term.clear()
end

local function updateValue(n)
  for _, v in pairs(valueUpdate) do
    if v[1] == n then return v[2]() end
  end
end

local function updateVariable(name, value)
  for _, v in pairs(valueUpdate) do
    if v[1] == name then
      v[3](value)
      break
    end
  end
end

local function calculateMinMax(min, max)
  return math.min(min, max), math.max(min, max)
end

local function pickerChanged()
  local btns = guiElements.mainButtons
  for i = #btns, 1, -2 do
    local e = btns[i]
    local e2 = btns[i-1]
    if e.getType() == "picker" and e2.getType() == "picker" then
      e.setMinValue(e2.getValue())
      e2.setMaxValue(e.getValue())
      e2.render()
    end
  end
end

local function newButton(x, y, w, h, text, bc, tc, action, name, b, callback)
  w = type(w) == "number" and w or 1
  h = math.max(type(h) == "number" and h or 1, 1)
  b = (type(b) == "number" and b >= 1 and b <= 3) and b or 1
  action = action or function() end
  callback = type(callback) == "function" and callback or function() end
  name = (type(name) == "string" and #name > 0) and name or "noName"
  local hx = x + math.ceil((w - #text) / 2)
  local hy = y + math.ceil(h / 2) - 1
  local enabled = true
  local dbc, dtc = colors.lightGray, colors.gray
  return {
    getType = function()
      return "button"
    end;
    getName = function()
      return name
    end;
    setDisabledColors = function(bc, tc)
      dbc = bc
      dtc = tc
    end;
    setText = function(t)
      text = t or ""
      hx = x + math.ceil((w - #text) / 2) --# recalculate offset
    end;
    setEnabled = function(e)
      enabled = e == true
    end;
    render = function()
      term.setBackgroundColor(enabled and bc or dbc)
      term.setTextColor(enabled and tc or dtc)
      for i = 0, h - 1 do
        term.setCursorPos(x, y + i)
        write(string.rep(' ', w))
      end
      term.setCursorPos(hx, hy)
      write(text)
    end;
    processEvent = function(event, button, xPos, yPos)
      if enabled and event == "mouse_click" and button == b and
        xPos >= x and xPos <= (x + w - 1) and
        yPos >= y and yPos <= (y + h - 1) then
        action()
        callback(name)
      end
    end;
  }
end

local function newNumberPicker(x, y, value, minVal, maxVal, name, enabled, callback)
  local w = #tostring(maxVal) + 2
  local bw = 3
  minVal, maxVal = calculateMinMax(minVal, maxVal)
  enabled = enabled == true
  local minus = newButton(x, y, bw, 1, '-', colors.gray, colors.white, function() value = math.max(value - 1, minVal) bPressed = "minus" end, name, 1, callback)
  local plus  = newButton(x + bw + w + 2, y, bw, 1, '+', colors.gray, colors.white, function() value = math.min(value + 1, maxVal) bPressed = "plus" end, name, 1, callback)
  minus.setDisabledColors(colors.gray, colors.lightGray)
  plus.setDisabledColors(colors.gray, colors.lightGray)
  return {
    getType = function()
      return "picker"
    end;
    getName = function()
      return name
    end;
    setEnabled = function(e)
      enabled = e == true
    end;
    setMinValue = function(v)
      minVal, maxVal = calculateMinMax(type(v) == "number" and v or minVal, maxVal)
    end;
    setMaxValue = function(v)
      minVal, maxVal = calculateMinMax(minVal, type(v) == "number" and v or maxVal)
    end;
    setValue = function(v)
      value = type(v) == "number" and v or value
    end;
    getValue = function()
      return value
    end;
    render = function()
      minus.setEnabled(enabled and value > minVal) --# update enabled status, this is really a bad place to do it...
      plus.setEnabled(enabled and value < maxVal)  --# ...but it's the only reliable place that gets called frequenty
      minus.render()                               --# draw the - and + buttons
      plus.render()
      term.setBackgroundColor(colors.white) 
      term.setTextColor(colors.black)
      term.setCursorPos(x + bw + 1, y)
      write(string.rep(' ', w))
      term.setCursorPos(x + bw + 1 + math.ceil((w - #tostring(value)) / 2), y)
      write(value)                                 --# draw the value
    end;
    processEvent = function(event, button, xPos, yPos)
      if event == "mouse_click" and button == 1 and yPos == y and enabled then --# reduces the amount of times the buttons need to process
        minus.processEvent(event, button, xPos, yPos)
        plus.processEvent(event, button, xPos, yPos)
      end
    end
  }
end

local function read( _mask, _history, _limit, _noTerminate )
  if _mask and type(_mask) ~= "string" then
    error("Invalid parameter #1: Expected string, got "..type(_mask), 2)
  end
  if _history and type(_history) ~= "table" then
    error("Invalid parameter #2: Expected table, got "..type(_history), 2)
  end
  if _limit and type(_limit) ~= "number" then
    error("Invalid parameter #3: Expected number, got "..type(_limit), 2)
  end
  if _noTerminate and type(_noTerminate) ~= "boolean" then
    error("Invalid argument #3: Expected boolean, got "..nativeType(_noTerminate), 2)
  end
  term.setCursorBlink(true)
  local input = ""
  local pos = 0
  local historyPos = nil
  local pullEvent = _noTerminate and os.pullEventRaw or os.pullEvent 
  local sw, sh = term.getSize()
  local sx, sy = term.getCursorPos()
  local function redraw( _special )
    local scroll = (sx + pos >= sw and (sx + pos) - sw or 0)
    local replace = _special or _mask
    local output = replace and (string.rep( replace, math.ceil(#input / #replace) - scroll )):sub(1, #input) or input:sub(scroll + 1)
    term.setCursorPos( sx, sy )
    term.write( output )
    term.setCursorPos( sx + pos - scroll, sy )
  end
  local nativeScroll = term.scroll
  term.scroll = function( _n ) local ok, err = pcall( function() return nativeScroll( _n ) end ) if ok then sy = sy - _n return err end error( err, 2 ) end
  while true do
    local event, code, mX, mY = pullEvent()
    if event == "char" and (not _limit or #input < _limit) then
      input = input:sub(1, pos)..code..input:sub(pos+1)
      pos = pos + 1
    elseif event == "paste" then
      input = input:sub(1, pos)..code..input:sub(pos+1)
      pos = pos + #code
    elseif event == "key" then
      if code == keys.enter or code == keys.numPadEnter then
        break
      elseif code == keys.backspace and pos > 0 then
        redraw(' ')
        input = input:sub(1, math.max(pos-1, 0))..input:sub(pos+1)
        pos = math.max(pos-1, 0)
      elseif code == keys.delete and pos < #input then
        redraw(' ')
        input = input:sub(1, pos)..input:sub(pos+2)
      elseif code == keys.home then
        pos = 0
      elseif code == keys["end"] then
        pos = #input
      elseif code == keys.left and pos > 0 then
        pos = math.max(pos-1, 0)
      elseif code == keys.right and pos < #input then
        pos = math.min(pos+1, #input)
      elseif _history and code == keys.up or code == keys.down then
        redraw(' ')
        if code == keys.up then
          if not historyPos then
            historyPos = #_history 
          elseif historyPos > 1 then
            historyPos = historyPos - 1
          end
        else
          if historyPos ~= nil and historyPos < #_history then
            historyPos = math.max(historyPos+1, #_history)
          elseif historyPos == #_history then
            historyPos = nil
          end
        end
        if historyPos and #_history > 0 then
          input = string.sub(_history[historyPos], 1, _limit) or ""
          pos = #input
        else
          input = ""
          pos = 0
        end
      end
    elseif event == "mouse_click" and (mX < sx or mX >= sx + _limit) or (mY ~= sy) then
      term.scroll = nativeScroll
      term.setCursorBlink(false)
      return input
    end
    redraw(_mask)
  end
  term.scroll = nativeScroll
  term.setCursorBlink(false)
  if sy + 1 > sh then
    term.scroll(sy + 1 - sh)
    term.setCursorPos(1, sy)
  else
    term.setCursorPos(1, sy + 1)
  end
  return input
end

local function formatTime(time)
  local hour = math.floor(time)
  local min = math.floor((time - hour) * 60)
  return string.format("%02d:%02d", hour, min)
end

local function getNumRockets()
  term.setCursorPos(41, 5)
  term.setBackgroundColor(colors.lightGray)
  term.setTextColor(colors.white)
  term.write("    ")
  term.setCursorPos(41, 5)
  local oldNumRockets = tostring(numRockets)
  local newCount = tonumber(read(nil, nil, 4))
  newCount = newCount and math.max(1, newCount) or numRockets
  numRockets = math.min(1000, newCount)
  for _, element in pairs(guiElements.mainButtons) do
    if element.getType() == "button" and (element.getName() == "numRockets" or element.getName() == "rocketPop") then
      local rockets = tostring(numRockets)
      element.setText(rockets)
      element.render()
    end
  end
end

local function getChance(which, posX, posY)
  term.setBackgroundColor(colors.lightGray)
  term.setTextColor(colors.white)
  term.setCursorPos(posX, posY)
  term.write("   ")
  term.setCursorPos(posX, posY)
  local newChance = tonumber(read(nil, nil, 3))
  if which == "shape" then
    newChance = newChance and math.max(0, newChance) or shapeChance
    shapeChance = math.min(100, newChance)
    for _, element in pairs(guiElements.mainButtons) do
      if element.getType() == "button" and (element.getName() == "shapeChance" or element.getName() == "shapePop") then
        local sChance = tostring(shapeChance)
        element.setText(sChance)
        element.render()
      end
    end
  elseif which == "effect" then
    newChance = newChance and math.max(0, newChance) or effectChance
    effectChance = math.min(100, newChance)
    for _, element in pairs(guiElements.mainButtons) do
      if element.getType() == "button" and (element.getName() == "effectChance" or element.getName() == "effectPop") then
        local eChance = tostring(effectChance)
        element.setText(eChance)
        element.render()
      end
    end
  end
end

local function switchShowType()
  showType = not showType
  term.setCursorPos(17, 5)
  term.setBackgroundColor(colors.black)
  term.setTextColor(colors.orange)
  term.write(showType and "Fancy   " or "Standard")
  minHeight, maxHeight = 1, 3
  if showType then
    minStars, maxStars = 1, 7
    minColors, maxColors = 1, 8
    shapeChance, effectChance = 25, 25
  else
    minStars, maxStars = 1, 3
    minColors, maxColors = 1, 3
    shapeChance, effectChance = 30, 30
  end
  for _, element in pairs(guiElements.mainButtons) do
    if element.getType() == "picker" then
      element.setEnabled(showType)
      element.setValue(updateValue(element.getName()))
    elseif element.getType() == "button" then
      if element.getName():find("show") then
        element.setText(showType and "::" or "..")
      elseif element.getName():find("shape") then
        local sChance = tostring(shapeChance)
        element.setText(sChance)
      elseif element.getName():find("effect") then
        local eChance = tostring(effectChance)
        element.setText(eChance)
      end
      if element.getName():find("Chance") then
        element.setEnabled(showType)
      end
    end
    element.render()
  end
  term.setBackgroundColor(colors.lightGray)
  term.setTextColor(showType and colors.white or colors.gray)
  term.setCursorPos(23, 14)
  term.write("%")
  term.setCursorPos(44, 14)
  term.write("%")
end

local function drawPopUpBox(x, y, w, text)
  term.setBackgroundColor(colors.white)
  term.setTextColor(colors.gray)
  for i = 1, 5 do --# draw the box
    term.setCursorPos(x, y + i)
    term.write(string.rep(" ", w))
  end
  for i = 1, 3 do --# write the text
    term.setCursorPos(x + 1, y + i + 1)
    term.write(text[i])
  end
end

local function drawPopUp(which)
  popUp = true
  if which == "shape" then       --# Shape Chance popup
    drawPopUpBox(4, 8, 23, { "The percentage chance", "any star will have a", "special shape" })
  elseif which == "effect" then  --# Effect Chance popup
    drawPopUpBox(24, 8, 23, { "The percentage chance", "any star will have a", "special effect" })
  elseif which == "pacing" then  --# Launch Timing popup
    drawPopUpBox(5, 10, 19, { "When ON, launches", "the fireworks at", "a more rapid pace" })
  elseif which == "finale" then  --# Finale Mode popup
    drawPopUpBox(4, 12, 21, { "When ON, launches", "the last 1/3 of the", "fireworks rapidly" })
  elseif which == "type" then    --# Show Type popup
    drawPopUpBox(18, 5, 19, { "Fancy allows you", "to customize your", "firework show" })
  elseif which == "rockets" then --# Number of Fireworks popup
    drawPopUpBox(31, 5, 18, { "Choose to launch", "from 1 to 1000", "fireworks" })
  end
end

local function drawSwitch(state)
  term.setBackgroundColor(state and colors.green or colors.gray)
  term.write("  ")
  term.setBackgroundColor(state and colors.gray or colors.red)
  term.write("  ")
end

local function drawHeader()
  clearScreen((operatingMode == "logs" or gettingHelp) and colors.white)
  local title = "StarCaster"
  if operatingMode == "show" then       --# Draw the header for active shows
    term.setCursorPos(1, 1)
    term.setTextColor(colors.yellow)
    term.setBackgroundColor(colors.red)
    term.write(string.rep(" ", math.floor(termX / 2) - math.floor(#title / 2)) .. title .. string.rep(" ", math.ceil(termX / 2) - math.ceil(#title / 2)))
    term.setCursorPos(1, 2)
    term.setBackgroundColor(colors.gray)
    term.setTextColor(colors.lightGray)
    local session = (sessionNumber > 10 and "Show  # " or "Show # ") .. sessionNumber
    term.write(string.rep(" ", math.floor(termX / 2) - math.ceil(#session / 2)) .. session .. string.rep(" ", math.ceil(termX / 2) - math.floor(#session / 2)))
    term.setCursorPos(termX - 9, 2)
    term.setBackgroundColor(colors.lightGray)
    term.setTextColor(colors.red)
    term.write(" Cancel ")
  else
    term.setTextColor(colors.white)
    for i = 1, 3 do                     --# Draw the main/help/log header with exit button
      term.setCursorPos(1, i)
      term.setBackgroundColor(colors.blue)
      term.write(string.rep(" ", termX - 3))
      term.setCursorPos(termX - 2, i)
      term.setBackgroundColor(colors.red)
      term.write(i == 2 and " X " or "   ")
    end
  end
  if operatingMode == "main" and not gettingHelp then --# Draw the log-view burger on main screen
    term.setTextColor(colors.white)
    term.setBackgroundColor(colors.lightBlue)
    for i = 1, 3 do
      term.setCursorPos(1, i)
      term.write(" - ")
    end
    term.setCursorPos(math.ceil(termX / 2 - math.floor(#title / 2) + 1), 2)
    term.setBackgroundColor(colors.blue)
    local nameColors = { colors.red, colors.yellow, colors.lime, colors.magenta }
    for i = 1, 4 do                     --# Main screen - write 'Star' with each letter in a different color
      term.setTextColor(nameColors[i])
      term.write(title:sub(i, i))
    end
    term.setTextColor(colors.lightBlue)
    term.write("Caster")                --# Main screen - write 'Caster'
  elseif operatingMode ~= "show" then
    if operatingMode == "logs" then
      title = "StarCaster Error Log"
    elseif gettingHelp then
      title = "StarCaster Help"
    end
    term.setBackgroundColor(colors.blue)
    term.setTextColor(colors.lightBlue)
    term.setCursorPos(math.ceil(termX / 2 - math.floor(#title / 2) + 1), 2)
    term.write(title)
  end
end

local function staticLaunchScreen()
  drawHeader()
  term.setCursorPos(2, 4)
  term.setBackgroundColor(colors.black)
  term.setTextColor(colors.gray)
  term.write("Show Type ")
  if not showType then
    term.setTextColor(colors.lightGray)
    if finaleMode then
      term.write("Std")
      term.setTextColor(colors.white)
      term.write("/")
      term.setTextColor(colors.lightGray)
      term.write("Finale")
    else
      term.write("Standard")
    end
  else
    local thisShow = "Fancy"
    for i = 1, 5 do
      term.setTextColor(2 ^ math.random(0, 14))
      term.write(thisShow:sub(i, i))
    end
    if finaleMode then
      term.setTextColor(colors.white)
      term.write("/")
      term.setTextColor(colors.lightGray)
      term.write("Finale")
    end
  end
  term.setTextColor(colors.gray)
  term.setCursorPos(2, 6)
  term.write("Rocket #")
  term.setCursorPos(2, 8)
  term.write("Height")
  term.setCursorPos(2, 10)
  term.write("Stars")
  term.setTextColor(colors.white)
  term.setCursorPos(25, 6)
  term.write("Errors")
  term.setTextColor(colors.gray)
  term.setCursorPos(25, 7)
  term.write("Star")
  term.setCursorPos(25, 8)
  term.write("Rocket")
  term.setCursorPos(25, 9)
  term.write("Ready")
  term.setCursorPos(25, 10)
  term.write("Launch")
  term.setCursorPos(termX - 7, 4)
  term.setTextColor(colors.white)
  term.write("(")
  term.setTextColor(colors.gray)
  term.write(showPacing and "Quick" or "Slow")
  term.setTextColor(colors.white)
  term.write(")")
end

local function displayLaunchInfo()
  term.setBackgroundColor(colors.black)
  term.setTextColor(colors.white)
  term.setCursorPos(12, 6)
  term.write(tostring(currentRocket)) --# current rocket
  term.setTextColor(colors.gray)
  term.write(" / ")
  term.setTextColor(colors.white)
  term.write(tostring(numRockets))   --# total # of rockets to launch (1-1000)
  term.setCursorPos(12, 8)
  term.write(tostring(launchHeight)) --# this rocket's launch height (1-3)
  for tmpY = 12, termY do
    term.setCursorPos(1, tmpY)
    term.write(string.rep(" ", termX))
  end
  for i = 1, numStars do             --# display star attributes
    term.setCursorPos(2, i + 11)
    term.setTextColor(colors.gray)
    term.write(tostring(i))          --# star #
    term.setCursorPos(4, i + 11)
    term.setTextColor(colors.white)
    if shapesList[i] then term.write(shapes[shapesList[i] + 1]) end    --# shape
    term.setCursorPos(18, i + 11)
    if effectsList[i] then term.write(effects[effectsList[i] + 1]) end --# effects
    term.setCursorPos(35, i + 11)
    term.setTextColor(colors.gray)
    term.write("Colors  ")
    term.setTextColor(colors.white)
    term.write(tostring(colorsList[i]))                              --# colors
  end
  term.setTextColor(colors.red)
  term.setCursorPos(32, 7)
  if starError then term.write(tostring(starError):sub(1, 19)) end     --# error in star manufacturing
  term.setCursorPos(32, 8)
  if buildError then term.write(tostring(buildError):sub(1, 19)) end   --# error in rocket manufacturing
  term.setCursorPos(32, 9)
  if readyError then term.write(tostring(readyError):sub(1, 19)) end   --# launcher not ready
  term.setCursorPos(32, 10)
  if launchError then term.write(tostring(launchError):sub(1, 19)) end --# error in launch
end

local function mainScreen()
  drawHeader()
  term.setCursorPos(6, 5)
  term.setBackgroundColor(colors.black)
  term.setTextColor(colors.white)
  term.write("Show type: ")
  term.setTextColor(colors.orange)
  term.write(showType and "Fancy" or "Standard")
  term.setTextColor(colors.white)
  term.setCursorPos(30, 5)
  term.write("Fireworks: ")
  term.setCursorPos(6, 8)
  term.write("Rocket Height")
  term.setCursorPos(6, 10)
  term.write("Stars/Rocket")
  term.setCursorPos(6, 12)
  term.write("Colors/Star")
  term.setCursorPos(6, 14)
  term.write("Shape Chance:")
  term.setCursorPos(6, 16)
  term.write("Launch Timing")
  term.setCursorPos(6, 18)
  term.write("Finale Mode")
  term.setCursorPos(26, 14)
  term.write("Effect Chance:")
  term.setCursorPos(22, 7)
  term.setTextColor(colors.gray)
  term.write("Minimum")
  term.setCursorPos(36, 7)
  term.write("Maximum")
  term.setTextColor(colors.black)
  local word, counter = "F1 Help", 0
  for i = 8, 15 do            --# F1/Help text
    counter = counter + 1
    term.setCursorPos(termX, i)
    term.write(word:sub(counter, counter))
  end
  term.setBackgroundColor(colors.lightGray)
  term.setTextColor(showType and colors.white or colors.gray)
  term.setCursorPos(23, 14)
  term.write("%")             --# shapeChance
  term.setCursorPos(44, 14)
  term.write("%")             --# effectChance
  term.setCursorPos(20, 16)
  drawSwitch(showPacing)      --# launch timing switch
  term.setCursorPos(20, 18)
  drawSwitch(finaleMode)      --# finale mode switch
  for _, element in pairs(guiElements.mainButtons) do
    if element.getType() == "button" then
        if element.getName():find("Chance") then
          element.setEnabled(showType)
        end
    end
    element.render()
  end
end

local function logScreen()
  term.setBackgroundColor(colors.white)
  for i = 4, termY do
    term.setCursorPos(1, i)
    term.write(string.rep(" ", termX))
  end
  pageNum = math.min(pageNum, numPages)
  if errorLog[1] then
    numPages = math.ceil(#errorLog / 13) 
    local currentEntry = ((pageNum - 1) * 12) + pageNum
    local yPos = 5
    for i = currentEntry, #errorLog do                 --# display log data
      term.setCursorPos(1, yPos)
      term.setTextColor(errorLog[i]:sub(1, 1) == "[" and colors.gray or colors.black)
      term.write(errorLog[i]:sub(1, 7))
      term.setTextColor(errorLog[i]:sub(1, 1) == "[" and colors.lightGray or colors.black)
      term.write(errorLog[i]:sub(8))
      yPos = yPos + 1
      if yPos == termY - 1 then break end
    end
    term.setCursorPos(1, termY)                         --# bottom row (for page buttons)
    term.setBackgroundColor(colors.lightGray)
    term.write(string.rep(" ", termX))
    for _, element in pairs(guiElements.logButtons) do --# page buttons
      element.render()
    end
    term.setTextColor(colors.white)                    --# pageNum of numPages
    local pages = tostring(pageNum) .. " of " .. tostring(numPages)
    term.setCursorPos(math.ceil(termX / 2) - math.floor(#pages / 2) + 1, termY)
    term.write(pages) 
  else
    term.setCursorPos(2, 5)
    term.setTextColor(colors.black)
    term.write("No log to display")
  end
end

local function helpScreen()
  drawHeader()
    --# Sidebar
  term.setBackgroundColor(colors.gray)
  term.setTextColor(colors.white)
  for i = 4, termY do
    term.setCursorPos(1, i)
    term.write(string.rep(" ", 10))
  end
    --# Sidebar contents
  term.setCursorPos(2, 7)
  term.write("Show")
  term.setCursorPos(2, 8)
  term.write("  Types")
  term.setCursorPos(2, 12)
  term.write("Launch")
  term.setCursorPos(2, 13)
  term.write(" Timing")
  term.setCursorPos(2, 15)
  term.write("Finale")
  term.setCursorPos(2, 16)
  term.write("   Mode")
  term.setCursorPos(2, 18)
  term.write("Chances")
    --# Help contents
  term.setBackgroundColor(colors.white)
  term.setTextColor(colors.black)
  term.setCursorPos(15, 6)
  term.write("Standard   1-3 stars/firework")
  term.setCursorPos(15, 7)
  term.setTextColor(colors.lightGray)
  term.write("(fixed)    ")
  term.setTextColor(colors.black)
  term.write("1-3 colors/star")
  term.setCursorPos(15, 9)
  term.write("Fancy      1-7 stars/firework")
  term.setCursorPos(15, 10)
  term.setTextColor(colors.lightGray)
  term.write("(custom)   ")
  term.setTextColor(colors.black)
  term.write("1-8 colors/star")
  term.setCursorPos(15, 12)
  term.write("Red        Loose and lazy")
  term.setCursorPos(15, 13)
  term.write("Green      Quick and tight")
  term.setCursorPos(15, 15)
  term.write("Fires the last third of the")
  term.setCursorPos(15, 16)
  term.write("fireworks in rapid succession")
  term.setCursorPos(15, 18)
  term.write("% chance of special shape/effect")
end

local function commandLineHelp()
  clearScreen()
  term.setCursorPos(2, 2)
  term.setTextColor(colors.lightBlue)
  term.write("StarCaster Commandline Help")
  term.setCursorPos(2, 4)
  term.setTextColor(colors.white)
  term.write("The following command line options are available")
  term.setCursorPos(2, 7)
  term.setTextColor(colors.lightGray)
  term.write("fancy  - start in 'Fancy' mode")
  term.setCursorPos(2, 9)
  term.write("fast   - start with 'Quick' launch timing enabled")
  term.setCursorPos(2, 11)
  term.write("finale - start with 'Finale Mode' enabled")
  term.setCursorPos(2, 13)
  term.write("debug  - auto-start a fireworks show in debug mode")
  term.setCursorPos(2, 16)
  term.setTextColor(colors.gray)
  term.write("Command line options may be combined")
  term.setTextColor(colors.white)
  term.setCursorPos(1, termY - 1)
end

local function clearTables()
  for i = 1, #colorsList do
    colorsList[i] = nil
  end
  for i = 1, #shapesList do
    shapesList[i] = nil
  end
  for i = 1, #effectsList do
    effectsList[i] = nil
  end
  for i = 1, #starIDTable do
    starIDTable[i] = nil
  end
  for i = 1, #rocketIDTable do
    rocketIDTable[i] = nil
  end
  starsMade = 0
  starColors = 0
end

local function clearInventory()
  for k, v in pairs(effectsAvailable) do
    effectsAvailable[k] = 0
  end
  for k, v in pairs(shapesAvailable) do
    shapesAvailable[k] = 0
  end
  for k, v in pairs(colorsAvailable) do
    colorsAvailable[k] = 0
  end
  for k, v in pairs(rocketParts) do
    rocketParts[k] = 0
  end
end

local function clearErrors()
  starError = "none" .. string.rep(" ", 15)
  buildError = "none" .. string.rep(" ", 15)
  readyError = "none" .. string.rep(" ", 15)
  launchError = "none" .. string.rep(" ", 15)
end

local function errorScreen()
  launchTimer = nil
  screenTimer = nil
  clearScreen(colors.white)
  term.setTextColor(colors.white)
  term.setBackgroundColor(colors.red)
  for i = 1, 3 do
    term.setCursorPos(1, i)
    term.write(string.rep(" ", termX))
  end
  term.setCursorPos(14, 2)
  term.write("StarCaster Critical Error")
  term.setTextColor(colors.red)
  term.setBackgroundColor(colors.white)
  term.setCursorPos(2, 5)
  term.write("Firework show aborted.")
  if badInventory then
    term.setCursorPos(2, 7)
    term.write("Missing critical components.")
    term.setTextColor(colors.gray)
    term.setCursorPos(2, 9)
    term.write("Please ensure you have the following:")
    term.setTextColor(colors.black)
    term.setCursorPos(2, 11)
    term.write("Gunpowder, paper, and dyes are required.")
    term.setTextColor(colors.gray)
    term.setCursorPos(2, 13)
    term.write("Optional Items:")
    term.setTextColor(colors.black)
    term.setCursorPos(2, 15)
    term.write("Effects: glowstone dust/diamonds")
    term.setCursorPos(2, 16)
    term.write("Shapes: gold nugget/feather/mob head/fire charge") 
  else
    local num = (#errorLog > 0) and math.min(9, #errorLog) or 0
    if num > 0 then 
      term.setTextColor(colors.red)
      term.setCursorPos(2, 7)
      term.write("The following are the last " .. num .. " log entries:")
      term.setTextColor(colors.black)
      local yPos = 9
      for i = #errorLog - (num - 1), #errorLog do
        term.setCursorPos(2, yPos)
        term.write(errorLog[i])
        yPos = yPos + 1
      end
    else
      term.setCursorPos(2, 7)
      term.write("There are no errors to display.")
    end
  end
  term.setTextColor(colors.lightGray)
  term.setCursorPos(10, termY)
  term.write("Click mouse button to continue")
end

local function postError()
  clearScreen()
  term.setCursorPos(1, 1)
  badWolf = false
  badInventory = false
  currentRocket = 1
  waitTime = 0.10
  timerTicker = 0
  operatingMode = "main"
  abortCounter = 0
  clearTables()
  clearErrors()
end

local function drawTimer()
  term.setBackgroundColor(colors.black)
  term.setTextColor(colors.white)
  term.setCursorPos(25, 4)
  term.setTextColor(colors.gray)
  term.write("Timer: ")
  term.setTextColor(colors.white)
  term.write(waitTime .. " / " .. timerTicker .. "   ")
end

local function adjustTimer()
    --# Set timing for next launch
  if currentRocket >= math.ceil(numRockets * 0.66) + 1 and finaleMode and not wereDoneHere and not goFinale then goFinale = true end
  if goFinale then
    waitTime = 0.10 * math.random(3, 5)     --# = 0.30
  else
    if showPacing then
      waitTime = 0.10 * math.random(7, 12)  --# 5, 10
    else
      waitTime = 0.10 * math.random(14, 19) --# 12, 17
    end
  end
end

local function logError(errorMessage)
  local logTime = formatTime(os.time())
  errorLog[#errorLog + 1] = "[" .. logTime .. "] " .. errorMessage
end

local function goodShot()
  clearTables()
  currentRocket = currentRocket + 1
  if runMode == "debug" then
    print("Launch: Successful")
  else
    clearErrors()
  end
end

local function badShot()
  clearTables()
  if runMode == "debug" then
    print("Launch: " .. launchError)
  else
    logError(launchError)
  end
  waitTime = 0.10
end

local function notReady()
  clearTables()
  readyError = #rocketIDTable > 0 and "Launcher not ready" or "No Rocket to Launch"
  if runMode == "debug" then
    print(readyError)
  else
    logError(readyError)
  end
  waitTime = 0.10
end

local function countItems(list)
  local count = 0
  for _ in pairs(list) do
    count = count + 1
  end
  return count
end

local function inventoryStar(id)
  starColors = 0
  local thisStar
  local tmpEffects = { }
  local colorBurst = { [1] = "White", [2] = "Orange", [4] = "Magenta", [8] = "Light Blue", [16] = "Yellow", [32] = "Lime", [64] = "Pink", [128] = "Gray", [256] = "Light Gray", [512] = "Cyan", [1024] = "Purple", [2048] = "Blue", [4096] = "Brown", [8192] = "Green", [16384] = "Red", [32768] = "Black", }
  if type(id) == "table" then                      --# if 'id' is a table then it's from a firework
    thisStar = id                                  --# set thisStar to point to id table
  else                                             --# otherwise 'id' is an actual id representing a physical star
    thisStar = launcher.inspectFireworkStar(id)    --# get info on the star
    starIDTable[#starIDTable + 1] = id             --# add star's ID to starIDTable
  end
  local foundShape = false
  for l, m in pairs(thisStar) do                   --# Begin processing the star
    if not foundShape then
      for k, v in pairs(shapes) do                 --# shapeLookup
        if m:find(v:sub(1, 4)) then                --# if a match is found...
          shapesList[#shapesList + 1] = k - 1      --# ...add shape to shapesList table...
          foundShape = true                        --# ...and indicate that we've found the shape
          break
        end
      end
    end
    for k, v in pairs(colorBurst) do               --# color lookup
      if m:lower():gsub("%s*", "") == v:lower():gsub("%s*", "") then --# if a matching color is found...
        starColors = colors.combine(starColors, k) --# ...add it to the starColors table
        break
      end
    end
    for k, v in pairs(effectsLookup) do            --# effect lookup
      if m:find(v) then                            --# if a match is found...
        tmpEffects[#tmpEffects + 1] = k - 1        --# ...add effect to tmpEffects table
        break
      end
    end
  end
  if not foundShape then shapesList[#shapesList + 1] = 0 end --# ensure a valid shape
  local newEffect
  if #tmpEffects == 2 then                                   --# if the number of effects is 2...
    newEffect = 3                                            --# ...set the effect to 'both'...
  else
    newEffect = tmpEffects[1]                                --# ...otherwise set the effect to the first (only)
  end                                                        --#   tmpEffects table entry
  if not newEffect or #tmpEffects < 1 then newEffect = 0 end --# ensure a valid effect
  effectsList[#effectsList + 1] = newEffect                  --# add effect to effectsList table
  colorsList[#colorsList + 1] = starColors                   --# add colors to colorsList table
end

local function inventoryFirework(id)
  local fwStarEntries = { }
  local thisFirework = launcher.inspectFireworkRocket(id) --# get info on the requested rocket id
  numStars = 0
  if thisFirework[1] then
    launchHeight = tonumber(thisFirework[1]:sub(18, 18))
    for i = 2, #thisFirework do                   --# This looks for the defined shape of each firework
      for k, v in pairs(shapes) do                --#   and creates a table of star index positions that
        if thisFirework[i]:find(v:sub(1, 4)) then --#     will allow us to parse the data 'star by star'.
          fwStarEntries[#fwStarEntries + 1] = i   --# Store the line number of the main table entry as the
          break                                   --#   next star entry index in fwStarEntries
        end
      end
    end
    numStars = #fwStarEntries                    --# the number of stars equals the number of star entries
    for i = 1, #fwStarEntries do                 --# process each star in sequence
      local newStar = { }                        --# this table will hold the info for each star as it's inventoried
      local startEntry = fwStarEntries[i]        --# set the 'start' entry
      local stopEntry = fwStarEntries[i + 1] and fwStarEntries[i + 1] - 1 or #thisFirework --# set the 'stop' entry
      local itemCount = 0                        --# initialize counter to track the number of items/star
      for j = startEntry, stopEntry do           --# collect data for this star, ending at the entry before the first entry for the next star
        itemCount = itemCount + 1                --# increment the item count for this star
        newStar[itemCount] = thisFirework[j]     --# record the item (shape/color/effect)
      end
      inventoryStar(newStar)                     --# send the newStar table to the star inventory routine
    end
  else                                --# empty rocket (no color/shape/effect - only paper and gp)
    launchHeight = 1                  --# provide generic launchHeight so the program doesn't choke
    numStars = 1                      --# provide a star to display
    shapesList[#shapesList + 1] = 5   --# provide generic shape to keep shapes table in sync
    effectsList[#effectsList + 1] = 0 --# provide generic effect to keep effects table in sync
    colorsList[#colorsList + 1] = 0   --# provide generic color to keep colors table in sync
  end
end

local function selectStarShape()
  starShape = 0
  local shapeWheel = { }
  local shapesCount = countItems(shapesAvailable) --# get the number of available shapes in inventory
  if shapesCount > 0 then                         --# if there is at least 1 shape...
    for k, v in pairs(shapesAvailable) do         --# ...go through the shapesAvailable table and...
      if v > 0 then                               --# ...if there is at least 1 shape...
        shapeWheel[#shapeWheel + 1] = k           --# ...add it to the shapeWheel table
      end
    end
    if #shapeWheel < 1 then                       --# if there are no shapes available...
      starShape = 0                               --# ...set the shape to default (small ball)...
    else                                          --# ...otherwise randomize for a possible shape
      starShape = math.random(1, 100) <= shapeChance and math.random(1, #shapeWheel) or 0 --# chance of special shape
    end
    if starShape > 0 then             --# if the starShape is 'special' then...
      local newStarShape = 0          --# set temp shape to 'Small Ball' in case the inventory of the shape generated is exhausted
      for i = 1, #ingredientValues do --#...find the shape in the ingredients list and decrement the inventory accordingly
        if shapeWheel[starShape]:find(ingredientValues[i][1]) and shapesAvailable[ingredientValues[i][1]] > 0 then
          shapesAvailable[ingredientValues[i][1]] = shapesAvailable[ingredientValues[i][1]] - 1
          newStarShape = ingredientValues[i][2]   --# set the shape value
          break
        end
      end
      starShape = newStarShape                    --# commit to the new shape
    end
    shapesList[#shapesList + 1] = starShape       --# add shape entry to shapesList table
  else
    starShape = 0                                 --# no special star shape generated (use Small Ball)
    shapesList[#shapesList + 1] = starShape       --# add shape entry to shapesList table
  end
  return true
end

local function selectStarEffects()
  starEffect = 0
  local effectWheel = { }
    --# generate the effect
  local effectsCount = countItems(effectsAvailable) --# get the number of available effects in inventory
  if effectsCount > 0 then                          --# if there's at least 1 effect...
    for k, v in pairs(effectsAvailable) do          --# ...go through the effectsAvailalbe table and...
      if v > 0 then                                 --# ...if there is at least 1 effect...
        effectWheel[#effectWheel + 1] = k           --# ...add it to the effectWheel table
      end
    end
    if #effectWheel < 1 then                        --# if there are no effects available...
      starEffect = 0                                --# ...set the effect to 'none'...
    else                                            --# ...otherwise randomize for a possible effect
      starEffect = (math.random(1, 100) <= effectChance) and math.random(1, #effectWheel + 1) or 0 --# chance to generate either trail, sparkle, both, or none
    end
    if #effectWheel < 2 then starEffect = math.min(starEffect, #effectWheel) end  --# ensure a valid value
    if starEffect > 0 and starEffect <= #effectWheel then --# if a starEffect is generated...
      local newStarEffect = 0                             --# set temp effect to 'none' in case the inventory of the effect generated is exhausted
      for i = 1, #ingredientValues do                     --# ...find the effect in the ingredients list and decrement the inventory accordingly
        if effectWheel[starEffect]:find(ingredientValues[i][1]) and effectsAvailable[ingredientValues[i][1]] > 0 then
          effectsAvailable[ingredientValues[i][1]] = effectsAvailable[ingredientValues[i][1]] - 1
          newStarEffect = ingredientValues[i][2]    --# set the effect value
          break
        end
      end
      starEffect = newStarEffect                    --# commit to the new effect
    end
    effectsList[#effectsList + 1] = starEffect      --# add effect entry to effectsList table
  else
    starEffect = 0                                  --# no special star effect generated (use 'none')
    effectsList[#effectsList + 1] = starEffect      --# add effect entry to effectsList table
  end
  return true
end

local function selectStarColors()
  starColors = 0
  local colorWheel = { }
    --# generate the colors
  local colorsCount = countItems(colorsAvailable) --# determine the number of colors available
  if colorsCount > 0 then                         --# if there is at least 1 color available...
    local numColors = math.random(minColors, maxColors) --# ...choose a random color from the list
    local magicNumber = math.min(rocketParts["item.sulphur"] - launchHeight, colorsCount) --# set a base minimum based on gunpowder and # of colors available
    numColors = math.min(numColors, magicNumber)  --# adjust # of colors based on the magicNumber
    local debit = 0                               --# this tracks the number of crafting spaces required for shapes and effects
    if #shapesList > #colorsList then             --# if a special shape is assigned...
      debit = debit + 1                           --# ...increment debit counter to reduce number of colors used
    end
    if #effectsList > #colorsList then            --# if a special effect is assigned...
      if effectsList[#effectsList] == 3 then      --# parse the effect to see if it's 1 or 2 effects
        debit = debit + 2                         --# two effects results in a reduction of the number of colors by 2
      else
        debit = debit + 1                         --# one effect results in a reduction of the number of colors by 1
      end
    end
    numColors = math.min(numColors, 8 - debit)    --# adjust numColors downward (as necessary) so we don't try to use too many crafting slots
    for i = 1, numColors do                       --# begin generating the list of available colors
      for k, v in pairs(colorsAvailable) do       --#   from the main inventory
        if v > 0 then                             --# if the amount of the color selected in inventory is > 1...
          colorWheel[#colorWheel + 1] = k         --# ...add the entry to the colorWheel for processing
        end
      end
      local newColor = colorWheel[math.random(1, #colorWheel)]  --# generate a random color from the colorWheel
      starColors = colors.combine(starColors, newColor)         --# add the color to the current list of colors for this star
      colorsAvailable[newColor] = colorsAvailable[newColor] - 1 --# decrement the inventory entry for the color
    end
    colorsList[#colorsList + 1] = starColors                    --# add the combined color value to the colorList table
    return true
  else
    starError = "Make Star: Out of color"
    if runMode == "debug" then
      print(starError)
    else
      logError(starError)
    end
    badWolf, badInventory = true, true
    return false
  end
end

local function makeStar()
  if not selectStarShape() then return false end   --# if there is no defined shape, fail the process
  if not selectStarEffects() then return false end --# if there is no defined effect, fail the process
  if not selectStarColors() then return false end  --# if there is no defined color, fail the process
  return launcher.craftFireworkStar(starColors, starShape, starEffect) --# craft the star and return the result
end

local function assembleStars(numberToMake)
  local starFailures, starNum = 0, 1
  for i = 1, numberToMake * 3 do             --# this allows an average of 3 tries per star
    local starSuccess, starID = makeStar()   --# call the makeStar() process
    if starSuccess then                      --# if successful...
      rocketParts["item.sulphur"] = rocketParts["item.sulphur"] - 1 --# ...remove 1 gun powder from the resource list
      starIDTable[#starIDTable + 1] = starID --# update the starIDTable
      if runMode == "debug" then
        print("Make Star # " .. starNum)
      end
      starNum = starNum + 1     --# which star the builder is currently working on
      starsMade = starsMade + 1 --# how many stars have been made
      if starsMade >= numberToMake then return true end --# if all stars are complete return true
    else                        --# if the star isn't made for some reason, decrement the star counter for a retry and increment the fail counter
      if starID then
        starError = starID
        if starError:find("gunpowder") then
          badWolf, badInventory = true, true
        end
      else
        starError = "Unknown failure"
      end
      if runMode == "debug" then
        print("Star#" .. starNum .. ": " .. starError)
      else
        logError(starNum .. ":" .. starError)
      end
      if badWolf then return false end
      starFailures = starFailures + 1
    end
  end
  if runMode == "debug" then
    print("Make Star: " .. starFailures .. " failures")
  else
    logError("Make Star: " .. starFailures .. " failures")
  end
  if starsMade > 1 then --# if the number of stars to be made is > 1...
    numStars = math.min(starsMade, numStars) --# ...set numStars equal to the lower value between starsMade and numStars
    return true         --# if at least one star was made allow the rocket to be finished
  else
    return false        --# no stars made - complete failure
  end
end

local function processStars()
  local starsBuilt = launcher.getFireworkStarIds() --# get the number of stars already existing
  if #starsBuilt > 0 then   --# query any pre-made stars
    for i = 1, #starsBuilt do
      inventoryStar(starsBuilt[i])
    end
  end
  starsMade = #starsBuilt   --# set the number of stars already made to equal the number of stars already existing
  local starsToBeMade = math.max(0, numStars - starsMade) --# adjust the number of stars to be made downward as necessary
  if runMode == "debug" then
    print("# of stars planned / built: " .. numStars .. " / " .. starsMade)
    print("# of stars to be made: " .. starsToBeMade)
  end
  if starsToBeMade < 1 then --# if there are no more stars to make
    return true             --# return true
  else                      --# otherwise...
    return(assembleStars(starsToBeMade)) --# make the number of stars remaining and return the result
  end
end

local function assembleFirework()
  local firework
  if runMode == "debug" then
    print("Start Rocket # " .. currentRocket)
  end
  for k, v in pairs(rocketParts) do
    if k:find("sulphur") and v >= launchHeight then --# double check if we have enough gunpowder (we already checked paper)
      firework, buildError = pcall(launcher.craftFireworkRocket, launchHeight, starIDTable) --# assemble the firework
      if firework then                              --# if the build was successful...
        rocketParts["item.sulphur"] = rocketParts["item.sulphur"] - launchHeight --# decrement the inventory
        rocketParts["item.paper"] = rocketParts["item.paper"] - 1
        if runMode == "debug" then
          print("Rocket # " .. currentRocket .. " built")
        end
        return true
      else                                          --# if the build was unsuccessful, log an error and return false
        if runMode == "debug" then
          print(buildError)
        else
          logError(buildError)
          if buildError:find("gunpowder") or buildError:find("paper") then
            badWolf, badInventory = true, true
          end
        end
        return false
      end
    end
  end
  badWolf, badInventory = true, true
  if runMode == "debug" then
    print("Abort Assembly: Missing critical materials")
  else
    logError("Abort Assembly: Missing critical materials")
  end
  return false
end

local function launchFirework(id)
  local fireThatSucker
  if launcher.canLaunch() then
    if id then
      fireThatSucker, launchError = pcall(launcher.launchSpecific, id)
    else
      fireThatSucker, launchError = pcall(launcher.launch)
    end
  else
    notReady()
    return false
  end
  if fireThatSucker then
    goodShot()
    return true
  else
    badShot()
    return false
  end
end

local function takeStock() --# get a listing of all the items in the inventory
  local items = { }
  for slot = 1, launcher.getInventorySize() do
    local stack = launcher.getStackInSlot(slot)
    if stack then --# construct a new table with only the info we need, the name, the qty, and the damage (to tell dyes apart)
      table.insert(items, { raw = stack.raw_name; qty = stack.qty; dmg = stack.dmg })
    end
  end
  return items
end

local function dyeToColor(stack)
  assert(stack.raw:find("item.dyepowder"), "item stack supplied is not a dye")
  return 2 ^ (15 - stack.dmg) --# CC colours are the opposite to MC colours, so in CC white is 0, but in MC it is 15, so we must flip it
end

local function filter(stock) --# filter out the listings of everything that is in the launcher to check for what we have that we want
  for _, info in pairs(stock) do                --# for all the items
    for _, v in pairs(inventoryCrossRef) do     --# for all that we want
      if info.raw:find(v[1]) then               --# if the raw name contains what we're looking for i.e. item.dyepowder will be found in item.dyepowder.blue
        local ok, col = pcall(dyeToColor, info) --# attempt to convert it to a colour
        if ok then                              --# if converstion worked
          v[2][col] = (v[2][col] and v[2][col] or 0) + info.qty --# update the count of it
        else
          v[2][info.raw] = (v[2][info.raw] and v[2][info.raw] or 0) + info.qty --# update the count of the other item
        end
      end
    end
  end
end

local function checkCreative()
  if launcher.isCreativeLauncher() then
    colorsAvailable = { [1] = 10000, [2] = 10000, [4] = 10000, [8] = 10000, [16] = 10000, [32] = 10000, [64] = 10000, [128] = 10000, [256] = 10000, [512] = 10000, [1024] = 10000, [2048] = 10000, [4096] = 10000, [8192] = 10000, [16384] = 10000, [32768] = 10000 }
    effectsAvailable = { ["item.diamond"] = 10000, ["item.yellowdust"] = 10000 }
    shapesAvailable = { ["item.feather"] = 10000, ["item.fireball"] = 10000, ["item.goldnugget"] = 10000, ["item.skull.skeleton"] = 10000 }
    rocketParts = { ["item.sulphur"] = 10000, ["item.paper"] = 10000 }
    return true
  end
  return false
end

local function checkInventory()
  clearInventory()
  if checkCreative() then return true end
  local gp, gs, co = false, false, false
  filter(takeStock()) --# at this point the item tables should contain everything that the inventory has, that we need
  for k, v in pairs(rocketParts) do
    if k:find("sulphur") and v > 1 then   --# need at least two GP - 1 for rocket, 1 for star
      gp = true
    end
    if k:find("paper") and v > 0 then     --# need at least 1 paper for the rocket
      gs = true
    end
  end
  if countItems(colorsAvailable) > 0 then --# need at least 1 color for a star
    co = true
  end
  if gp and gs and co then                --# if there are sufficient resources to build at least 1 rocket...
    return true                           --# ...return true
  end
  return false                            --# ...otherwise return false
end

local function minRequirements()
  local newMaxHeight = math.min(maxHeight, rocketParts["item.sulphur"] - 1) --# adjust max launchHeight downward to account for low gunpowder (leaving 1 gunpowder for a star)
  if newMaxHeight < 1 then                      --# if there is < 1 gunpowder then error out
    if runMode == "debug" then
      print("Rocket: Insufficient gunpowder")
    else
      logError("Rocket: Insufficient gunpowder")
      badWolf, badInventory = true, true
    end
    return false
  end
  if rocketParts["item.paper"] < 1 then         --# if there is < 1 paper then error out
    if runMode == "debug" then
      print("Rocket: Insufficient paper")
    else
      logError("Rocket: Insufficient paper")
      badWolf, badInventory = true, true
    end
    return false
  end
  local newMinHeight = minHeight
  if goFinale then newMinHeight = math.max(2, minHeight) end --# if the finale has started, raise the minimum launch height to 2 if it is 1
  newMinHeight = math.min(newMinHeight, newMaxHeight)        --# adjust the minimum height down as necessary
  launchHeight = math.random(newMinHeight, newMaxHeight)     --# set the launchHeight
  numStars = math.random(minStars, maxStars)                 --# determine the number of stars for the firework
  if launchHeight == 3 then numStars = math.min(numStars, 5) end --# adjust number of stars down as for launchHeight
  if launchHeight == 2 then numStars = math.min(numStars, 6) end --# adjust number of stars down for launchHeight
  numStars = math.min(numStars, rocketParts["item.sulphur"] - launchHeight) --# adjust number of stars down if gunpowder is low
  numStars = math.min(numStars, countItems(colorsAvailable)) --# adjust number of stars down if colors are low 
  if numStars < 1 then                                       --# if the adjusted number of stars is < 1 error out
    if runMode == "debug" then
      print("Stars: Insufficient gunpowder or color")
    else
      logError("Stars: Insufficient gunpowder or color")
      badWolf, badInventory = true, true
    end
    return false
  end
  return true
end

local function workFlow()
  if badWolf then return false end
  rocketIDTable = launcher.getFireworkRocketIds() --# collect IDs of any rockets in the buffer
  if #rocketIDTable > 0 then            --# if there is at least 1 rocket in the buffer then get info and launch, otherwise generate a new one
    if runMode == "debug" then
      print("Using finished rocket")
    end
    inventoryFirework(rocketIDTable[1]) --# a rocket exists - get rocket info (launchHeight and star inventory) and launch
    if runMode == "debug" then
      print("Launching rocket")
    else
      displayLaunchInfo()
    end
    return launchFirework()             --# launch the pre-built rocket
  else                                  --# if no rocket is pre-existing, then build and launch a rocket
    if not minRequirements() then return false end --# sanity check - this ensures we have enough gunpowder, paper, and colors to launch at least one firework
    if processStars() then              --# create stars
      if runMode ~= "debug" then displayLaunchInfo() end
      if assembleFirework() then        --# assemble the rocket
        if launchFirework() then        --# launch the bird
          return true
        end
      end
      return false
    end
    return false
  end
end

local function launchControl()
  if badWolf then return false end
  local goTeam = workFlow()            --# process and launch the next firework
  if goTeam then                       --# if the process was successful...
    if currentRocket > numRockets then --# ...check if the show is over then stop if it is
      if runMode == "debug" then
        runMode = "stop"
        operatingMode = "debug"
      else
        waitTime = 0
        wereDoneHere = true
        screenTimer = os.startTimer(2)
        return
      end
    else
      adjustTimer()                    --# ...if the show isn't over, adjust the timer and continue
    end
  else
    abortCounter = abortCounter + 1    --# a major failure of some sort (not show stopping) - increment the abort counter
    waitTime = 0.10
    if abortCounter >= 3 then          --# 3 major failures - time to stop the show
      if runMode == "debug" then
        print("Too many failures, aborting")
      else
        logError("Too many failures, aborting")
      end
      badWolf = true
    else
      if runMode == "debug" then
        print("Unable to launch " .. abortCounter .. " times")
      else
        logError("Unable to launch " .. abortCounter .. " times")
      end
    end
  end
end

local function showControl()
  if badWolf then
    if runMode == "debug" then
      runMode = "stop"
      operatingMode = "debug"
    end
    return
  end
  local event, data, x, y = os.pullEvent()
  if event == "timer" and data == launchTimer then
    if runMode ~= "debug" then
      drawTimer()
    end
    timerTicker = timerTicker + 0.1 --# increment the timer counter
    if currentRocket <= numRockets and timerTicker >= waitTime then --# time to launch another firework
      timerTicker = 0
      if runMode == "debug" then
        print("Timer Cycle")
      end
      launchControl()        --# initiate the build/launch process
    end
    launchTimer = os.startTimer(TIMER_GRANULARITY)     --# restart the timer
  elseif event == "timer" and data == screenTimer then --# if the show is over return to the main screen
    operatingMode = "main"   --# this tells the main controller loop (scKernel) which functions to monitor
    waitTime = 0.10
    timerTicker = 0
    mainScreen()
  elseif event == "mouse_click" and runMode ~= "debug" and y == 2 and x > termX - 10 and x < termX - 1 then --# if the user clicks 'cancel', end the show and return to the main screen
    operatingMode = "main"   --# this tells the main controller loop (scKernel) which functions to monitor
    wereDoneHere = false
    goFinale = false
    currentRocket = 1  
    mainScreen()
  elseif event == "char" and (string.lower(data) == "q" or string.lower(data) == "c" or string.lower(data) == "x") then --# if the user types 'q', 'c', or 'x', end the show, and return to the main screen if not in debug mode
    if runMode == "debug" then
      operatingMode = "debug"
      runMode = "stop"
    else
      operatingMode = "main" --# this tells the main controller loop (scKernel) which functions to monitor
      wereDoneHere = false
      goFinale = false
      currentRocket = 1  
      mainScreen()
    end
  end
end

local function startShow()
  sessionNumber = sessionNumber + 1 --# Increment the session number
  if runMode ~= "debug" then        --# Create and insert the show # log separator
    local lastPart = (sessionNumber < 10 and string.rep("-", math.ceil(termX / 2) - (5 + math.floor(#tostring(sessionNumber))))  or string.rep("-", math.ceil(termX / 2) - (4 + math.floor(#tostring(sessionNumber)))))
    local separator = string.rep("-", math.floor(termX / 2) - (3 + math.ceil(#tostring(sessionNumber) / 2))) .. " Show" .. (sessionNumber < 10 and "  " or " ") .. "#" .. tostring(sessionNumber) .. " " .. lastPart
    if sessionNumber > 99 and sessionNumber < 1000 then
      separator = "-" .. separator
    elseif sessionNumber > 999 then
      separator = separator .. "-"
    end
    errorLog[#errorLog + 1] = separator
  end
  if not checkInventory() then      --# if there isn't sufficient inventory then error out
    operatingMode = "error"
    badWolf, badInventory = true, true
    if runMode == "debug" then
      print("Abort @ start: Missing critical components")
    else
      logError("Abort @ start: Missing critical components")
    end
    return false
  end                      --# if the program didn't error out then set the necessary variables and start the show
  operatingMode = "show"   --# this tells the controller loop (scKernel) which functions to monitor
  wereDoneHere = false
  goFinale = false
  starsMade = 0
  starColors = 0
  currentRocket = 1
  abortCounter = 0
  waitTime = 0.10
  timerTicker = 0
  numStars = 1
  if runMode ~= "debug" then staticLaunchScreen() end --# show the launch info screen if not in debug mode
  launchTimer = os.startTimer(TIMER_GRANULARITY)      --# this starts the launch timer which is managed by showControl()
end

local function selectPage()
  term.setBackgroundColor(colors.gray)
  term.setTextColor(colors.lightGray)
  for i = 1, 3 do                             --# draw the box
    term.setCursorPos(math.ceil(termX / 2) - 3, termY - 4 + i)
    term.write(string.rep(" ", 8))
  end
  term.setCursorPos(math.ceil(termX / 2) - 2, termY-3)
  term.write("Page #")                        --# draw the 'title'
  term.setCursorPos(math.ceil(termX / 2) - 2, termY-2)
  term.setBackgroundColor(colors.lightGray)
  term.write(string.rep(" ", 6))              --# draw the input area
  term.setCursorPos(math.ceil(termX / 2) - 1, termY-2)
  term.setTextColor(colors.white)
  local newPage = tonumber(read(nil, nil, 4)) --# get the selected page from the user
  if not newPage then newPage = pageNum end   --# if no input then stay on the same page
  newPage = math.max(1, newPage)              --# make sure page # is 1 or higher
  pageNum = math.min(newPage, numPages)       --# make sure page # is <= numPages
  drawHeader()
end

local function logInput()
  local event, button, x, y = os.pullEvent()
  if event == "mouse_click" then
    if x > 48 and y < 4 and button == 1 then --# exit button
      operatingMode = "main"
      mainScreen()
      return
    elseif x > 20 and x < 31 and y == termY and numPages > 1 then --# 'Go To Page'
      selectPage()
      logScreen()
    else
      for _, click in pairs(guiElements.logButtons) do --# process buttons
        click.processEvent(event, button, x, y)
      end
    end
  elseif event == "mouse_scroll" then
    if button == 1 then
      pageNum = math.min(numPages, pageNum + 1)
    elseif button == -1 then
      pageNum = math.max(1, pageNum - 1)
    end
    logScreen()
  elseif event == "key" then
    if button == keys.home then
      pageNum = 1
      logScreen()
    elseif button == keys["end"] then
      pageNum = numPages
      logScreen()
    elseif button == keys.pageUp then    --# 201
      pageNum = math.min(numPages, pageNum + 1)
      logScreen()
    elseif button == keys.pageDown then  --# 209
      pageNum = math.max(1, pageNum - 1)
      logScreen()
    end
  end
end

local function mainInput()
  local event, button, x, y = os.pullEvent()
  if event == "mouse_click" then
    if x > 48 and y < 4 and button == 1 and not popUp then    --# big red X
      if not gettingHelp then                                 --# exit StarCaster
        runMode = "stop"
        return
      elseif gettingHelp then                                 --# exit logs or help
        gettingHelp = false
        mainScreen()
        return
      end
    elseif x < 4 and y < 4  and button == 1 and not gettingHelp and not popUp then --# open logs
      operatingMode = "logs"
      drawHeader()
      logScreen()
      return
    elseif y == 14 and button == 2 and not gettingHelp and not popUp then
      if x > 19 and x < 24 then                               --# shapeChance popup
        drawPopUp("shape")
      elseif x > 40 and x < 45 then                           --# effectChance popup
        drawPopUp("effect")
      end
    elseif y == 16 and x > 19 and x < 24 and not gettingHelp and not popUp then --# showPacing (launch timing)
      if button == 1 then
        showPacing = not showPacing
        term.setCursorPos(20, 16)
        drawSwitch(showPacing)
      elseif button == 2 then
        drawPopUp("pacing")
      end
    elseif y == 18 and x > 19 and x < 24 and not gettingHelp and not popUp then --# finaleMode
      if button == 1 then
        finaleMode = not finaleMode
        term.setCursorPos(20, 18)
        drawSwitch(finaleMode)
      elseif button == 2 then
        drawPopUp("finale")
      end
    elseif y > 7 and y < 15 and x == termX and not gettingHelp and not popUp then --# F1 Help
      gettingHelp = true
      helpScreen()
    elseif popUp then
      popUp = false
      mainScreen()
    else
      if not gettingHelp and not popUp then
        for _, click in pairs(guiElements.mainButtons) do     --# process buttons
          click.processEvent(event, button, x, y)             --# carry out action for clicked button
          if click.getType() == "picker" and operatingMode == "main" and not gettingHelp and not popUp then --# this second full sanity check is necessary since we've already processed the button event and things have changed
            updateVariable(click.getName(), click.getValue()) --# update the actual variable associated with the button
            click.render()                                    --# draw buttons
          end
        end
      end
    end
  elseif event == "key" and button == keys.f1 then
    gettingHelp = not gettingHelp
    if gettingHelp then
      helpScreen()
    else
      mainScreen()
    end
  end
end

local function scKernel() --# this is the main loop
  while true do
    if badWolf and runMode ~= "debug" and runMode ~= "stop" then  --# if there's a 'critical' error show the error screen
      errorScreen()
      os.pullEvent("mouse_click")
      postError()
      mainScreen()
    elseif operatingMode == "main" and runMode == "standard" then --# main UI / input
      mainInput()
    elseif operatingMode == "logs" then                           --# logs UI / input
      logInput()
    elseif operatingMode == "show" then                           --# show UI / input
      showControl()
    elseif runMode == "debug" and operatingMode == "main" then    --# auto-start show when in debug mode
      startShow()
    end
    if runMode == "stop" then                                     --# exit and terminate program
      if operatingMode ~= "debug" then
        clearScreen()
        term.setTextColor(colors.white)
        term.setCursorPos(1, 1)
      end
      return
    end
  end
end

local function initHardware()
  local notFind, perp = false
  launcher = peripheral.find("firework_launcher")
  if not launcher then
    notFind = true
    perp = "a \n   MoarPeripherals Fireworks Launcher"
  end
  if not term.isColor() then
    notFind = true
    perp = "an \n   Advanced Computer"
  end
  if notFind then
    clearScreen()
    term.setTextColor(colors.white)
    term.setCursorPos(2, 2)
    write("StarCaster requires " .. perp)
    term.setCursorPos(1, 5)
    return false
  end
  if not checkInventory() then
    badWolf, badInventory = true, true
    errorScreen()
    os.pullEvent("mouse_click")
    clearScreen()
    term.setCursorPos(1, 1)
    runMode = "stop"
    return false
  end
  return true
end

if not initHardware() then return end

if tArgs[1] then
  if tArgs[1] == "?" or tArgs[1]:sub(1, 1) == "/" or tArgs[1]:sub(1, 1) == "-" then
    commandLineHelp()
    return
  else
    for i = 1, #tArgs do
      if string.lower(tArgs[i]) == "fancy" then
        showType = true
      elseif string.lower(tArgs[i]) == "fast" then
        showPacing = true
      elseif string.lower(tArgs[i]) == "finale" then
        finaleMode = true
      elseif string.lower(tArgs[i]) == "debug" then
        runMode = "debug"
      elseif string.lower(tArgs[i]) == "splash" then
        clearScreen()
        local splash = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32768, 32768, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, }, { 0, 0, 0, 0, 16384, 16384, 16384, 16384, 16384, 0, 0, 16, 16, 16, 16, 16, 32768, 0, 0, 0, 32, 0, 0, 0, 0, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 32768, 0, 0, 8192, 0, 0, 2, 0, 2048, 0, 0, 0, 0, }, { 0, 0, 0, 16384, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16, 0, 0, 0, 0, 32768, 32, 0, 32, 0, 0, 0, 4, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 1024, 0, 32768, 0, 0, 1024, 0, 0, 0, 0, 1, 0, 0, }, { 0, 0, 0, 0, 16384, 16384, 16384, 16384, 0, 0, 0, 0, 0, 16, 0, 0, 0, 32768, 32, 0, 0, 0, 32, 0, 0, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2048, 0, 0, 8192, 0, 32768, 0, 0, 8192, }, { 0, 0, 0, 0, 0, 0, 0, 32768, 16384, 0, 0, 0, 0, 16, 0, 0, 0, 32768, 32, 32, 32, 32, 32, 32768, 0, 4, 0, 32768, 32768, 4, 0, 0, 0, 0, 0, 8192, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 512, 0, 0, }, { 0, 0, 0, 16384, 16384, 16384, 16384, 16384, 0, 0, 0, 0, 0, 16, 0, 0, 32768, 32768, 32, 0, 0, 0, 32, 0, 0, 4, 0, 0, 32768, 4, 0, 0, 0, 32768, 0, 0, 0, 32768, 32768, 1024, 0, 0, 512, 0, 2, 0, 0, 16, 0, }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 8192, 0, 0, 0, 0, 1, 0, 32768, 0, }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 2048, 0, 0, 32768, 1024, 32768, }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32768, 32768, 32768, 32768, 32768, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1024, 0, 2, 0, 32768, 8192, 0, 0, 0, }, { 0, 0, 0, 0, 8, 8, 8, 8, 8, 0, 32768, 0, 8, 0, 0, 0, 0, 8, 8, 8, 8, 32768, 8, 8, 8, 8, 8, 32768, 8, 8, 8, 8, 32768, 32768, 8, 8, 8, 8, 0, 0, 0, 0, 0, 0, 0, 32768, 0, 32768, 0, }, { 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 32768, 8, 0, 8, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 8, 32768, 0, 0, 8, 0, 0, 0, 0, 0, 8, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 32768, 0, }, { 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 8, 32768, 0, 0, 8, 0, 0, 8, 8, 8, 32768, 0, 0, 0, 8, 32768, 0, 0, 8, 8, 8, 0, 0, 0, 8, 8, 8, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, }, { 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 8, 8, 8, 8, 8, 0, 0, 0, 0, 0, 8, 32768, 0, 0, 8, 0, 0, 0, 8, 32768, 0, 0, 0, 0, 8, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, }, { 0, 0, 0, 32768, 8, 8, 8, 8, 8, 0, 8, 32768, 0, 0, 8, 0, 8, 8, 8, 8, 0, 0, 0, 32768, 8, 0, 0, 0, 8, 8, 8, 8, 8, 0, 8, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, }, { 0, 0, 0, 0, 32768, 32768, 32768, 32768, 32768, 32768, 32768, 0, 0, 0, 32768, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, }, }
        paintutils.drawImage(splash, 1, 1)
        term.setCursorPos(15, termY - 1)
        term.setTextColor(colors.gray)
        term.write("By: Dog and TheOriginalBIT")
        sleep(1)
      end
    end
  end
end

if not showType then maxStars, maxColors = 3, 3 end

--# x pos, y pos, width, height, text, background color, text color, function, name, button to activate (optional)
guiElements = {
                mainButtons = {
                                newButton(26, 5, 2, 1, "..", colors.gray, colors.white, function() drawPopUp("type") end, "showPop", 2);
                                newButton(26, 5, 2, 1, "..", colors.gray, colors.white, function() switchShowType() end, "showType", 1);
                                newButton(41, 5, 4, 1, "25", colors.lightGray, colors.white, function() drawPopUp("rockets") end, "rocketPop", 2);
                                newButton(41, 5, 4, 1, "25", colors.lightGray, colors.white, function() getNumRockets() end, "numRockets", 1);
                                newButton(20, 14, 3, 1, "30", colors.lightGray, colors.white, function() getChance("shape", 20, 14) end, "shapeChance", 1);
                                newButton(41, 14, 3, 1, "30", colors.lightGray, colors.white, function() getChance("effect", 41, 14) end, "effectChance", 1);
                                newButton(26, 16, 19, 3, "Start  Show", colors.green, colors.white, function() startShow() end, "startShow", 1);
                                  --# add new buttons and pickers here, not at the end
                                newNumberPicker(20, 8, 1, 1, 3, "minHeight", false, pickerChanged);  --# rocketHeight min
                                newNumberPicker(34, 8, 3, 1, 3, "maxHeight", false, pickerChanged);  --# rocketHeight max
                                newNumberPicker(20, 10, 1, 1, 7, "minStars", false, pickerChanged);  --# minStars
                                newNumberPicker(34, 10, maxStars, 1, 7, "maxStars", false, pickerChanged);  --# maxStars
                                newNumberPicker(20, 12, 1, 1, 8, "minColors", false, pickerChanged); --# minColors
                                newNumberPicker(34, 12, maxColors, 1, 8, "maxColors", false, pickerChanged); --# maxColors
                              }, 
                logButtons = {
                               newButton(17, termY, 2, 1, "<<", colors.lightGray, colors.gray, function() pageNum = 1 logScreen() end, "home", 1);
                               newButton(20, termY, 1, 1, "<", colors.lightGray, colors.gray, function() pageNum = math.max(1, pageNum - 1) logScreen() end, "pageMinus", 1);
                               newButton(33, termY, 1, 1, ">", colors.lightGray, colors.gray, function() pageNum = math.min(numPages, pageNum + 1) logScreen() end, "pagePlus", 1);
                               newButton(35, termY, 2, 1, ">>", colors.lightGray, colors.gray, function() pageNum = numPages logScreen() end, "end", 1);
                             }, 
              }

if runMode == "standard" then
  mainScreen()
elseif runMode == "debug" then
  clearScreen()
  term.setCursorPos(1, 1)
end

scKernel() --# start the program
