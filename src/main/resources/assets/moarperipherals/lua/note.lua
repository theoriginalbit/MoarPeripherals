-- +---------------------+------------+---------------------+
-- |                     |            |                     |
-- |                     |  Note API  |                     |
-- |                     |            |                     |
-- +---------------------+------------+---------------------+

-- Note Block Song format + conversion tools:   David Norgren
-- Iron Note Block + NBS loading & playback:   TheOriginalBIT
-- Music player interface & API structure:         Bomb Bloke

-- ----------------------------------------------------------

-- Place Note Block Studio NBS files on your ComputerCraft computer,
-- then play them back via a MoarPeripheral's Iron Note Block!

-- http://www.computercraft.info/forums2/index.php?/topic/19357-moarperipherals
-- http://www.minecraftforum.net/topic/136749-minecraft-note-block-studio

-- This script can be ran as any other, but it can *also* be loaded as an API!
-- Doing so exposes the following functions:

-- < note.playSong(fileName) >
-- Simply plays the specified NBS file.

-- < note.songEngine([fileName]) >
-- Plays the optionally specified NBS file, but whether one is specified or not, does
-- not return when complete - instead this is intended to run continuously as a background
-- process. Launch it via the parallel API and run it alongside your own script!

--  While the song engine function is active, it can be manipulated by queuing the
--  following events:

--   * musicPlay
--   Add a filename in as a parameter to start playback, eg:
--   os.queueEvent("musicPlay","mySong.nbs")

--   * musicPause
--   Halts playback.

--   * musicResume
--   Resumes playback.

--   * musicSkipTo
--   Add a song position in as a parameter to skip to that segment. Specify the time
--   in tenths of a second; for example, to skip a minute in use 600.

--   Additionally, whenever the song engine finishes a track, it will automatically
--   throw a "musicFinished" event, or a "newTrack" event whenever a new song is loaded.

--  **Remember!** The API cannot respond to these events until YOUR code yields!
--  Telling it to load a new song or jump to a different position won't take effect
--  until you pull an event or something!

-- < note.setPeripheral(targetDevice1, [targetDevice2,] ...) >
-- By default, upon loading the API attaches itself to any Iron Note Blocks it detects.
-- Use this if you have specific note block(s) in mind, or wish to use different blocks
-- at different times - perhaps mid-song! Returns true if at least one of the specified
-- devices was valid, or false if none were.

--  **Note!** The Iron Note Block peripheral can currently play up to five instruments
--            at any given moment. Providing multiple blocks to the API will cause it to
--            automatically spread the load for those songs that need the extra notes.
--            If you provide insufficient blocks, expect some notes to be skipped from
--            certain songs.

--            Very few songs (if any) require more than two Iron Note Blocks.

-- < note.isPlaying() >
-- Returns whether the API is currently mid-tune (ignoring whether it's paused or not).

-- < note.isPaused() >
-- Returns whether playback is paused.

-- < note.getSongLength() >
-- Returns the song length in "redstone updates". There are ten updates per second, or
-- one per two game ticks.

-- < note.getSongPosition() >
-- Returns the song position in "redstone updates". Time in game ticks is 2 * this. Time
-- in seconds is this / 10.

-- < note.getSongSeconds() >
-- Returns the song length in seconds.

-- < note.getSongPositionSeconds() >
-- Returns the song position in seconds.

-- < note.getSongTempo() >
-- Returns the song tempo, representing the "beats per second".
-- Eg: 2.5 = one beat per 0.4 seconds.
--       5 = one beat per 0.2 seconds.
--      10 = one beat per 0.1 seconds.

-- Must be a factor of ten.

-- < note.getRealSongTempo() >
-- Returns the tempo the song's NBS file specified it should be played at.

-- Should be a power of ten, but for whatever reason a lot of NBS files have invalid tempos.

-- < note.getSongName() >
-- Returns the name of the song.

-- < note.getSongAuthor() >
-- Returns the name of the NBS file author.

-- < note.getSongArtist() >
-- Returns the name of the song artist.

-- < note.getSongDescription() >
-- Returns the song's description.

-- ----------------------------------------------------------

if not shell then
    -- -----------------------
    -- Load as API:
    -- -----------------------

    -- Cranking this value too high will cause crashes:
    local MAX_INSTRUMENTS_PER_NOTE_BLOCK = 5

    local ironnote, paused, cTick, song = {peripheral.find("iron_note")}

    local translate = {[0]=0,4,1,2,3}

    local function assert(cdn, msg, lvl)
        if not cdn then
            error(msg or "assertion failed!", (lvl == 0 and 0 or lvl and (lvl + 1) or 2))
        end
        return cdn
    end

    -- Returns the song length.
    function getSongLength()
        if type(song) == "table" then return song.length end
    end

    -- Returns the song position.
    function getSongPosition()
        return cTick
    end

    -- Returns the song length in seconds.
    function getSongSeconds()
        if type(song) == "table" then return song.length / song.tempo end
    end

    -- Returns the song position in seconds.
    function getSongPositionSeconds()
        if type(song) == "table" then return cTick / song.tempo end
    end

    -- Returns the tempo the song will be played at.
    function getSongTempo()
        if type(song) == "table" then return song.tempo end
    end

    -- Returns the tempo the song's NBS file specified it should be played at.
    function getRealSongTempo()
        if type(song) == "table" then return song.realTempo end
    end

    -- Switches to a different playback device.
    function setPeripheral(...)
        local newironnote = {}

        for i=1,#arg do if type(arg[i]) == "string" and peripheral.getType(arg[i]) == "iron_note" then
            newironnote[#newironnote+1] = peripheral.wrap(arg[i])
        elseif type(arg[i]) == "table" and arg[i].playNote then
            newironnote[#newironnote+1] = arg[i]
        end end

        if #newironnote > 0 then
            ironnote = newironnote
            return true
        else return false end
    end

    -- Returns whether music is loaded for playback.
    function isPlaying()
        return type(song) == "table"
    end

    -- Returns whether playback is paused.
    function isPaused()
        return paused
    end

    -- Returns the name of the song.
    function getSongName()
        if type(song) == "table" then return song.name end
    end

    -- Returns the name of NBS file author.
    function getSongAuthor()
        if type(song) == "table" then return song.author end
    end

    -- Returns the name of song artist.
    function getSongArtist()
        if type(song) == "table" then return song.originalauthor end
    end

    -- Returns the song's description.
    function getSongDescription()
        if type(song) == "table" then return song.description end
    end

    local function byte_lsb(handle)
        return assert(handle.read(), "Note NBS loading error: Unexpected EOF (end of file).", 2)
    end

    local function byte_msb(handle)
        local x = byte_lsb(handle)
        --# convert little-endian to big-endian
        local y = 0
        y = y + bit.blshift(bit.band(x, 0x00), 7)
        y = y + bit.blshift(bit.band(x, 0x02), 6)
        y = y + bit.blshift(bit.band(x, 0x04), 5)
        y = y + bit.blshift(bit.band(x, 0x08), 4)
        y = y + bit.brshift(bit.band(x, 0x10), 4)
        y = y + bit.brshift(bit.band(x, 0x20), 5)
        y = y + bit.brshift(bit.band(x, 0x40), 6)
        y = y + bit.brshift(bit.band(x, 0x80), 7)
        return y
    end

    local function int16_lsb(handle)
        return bit.bor(bit.blshift(byte_lsb(handle), 8), byte_lsb(handle))
    end

    local function int16_msb(handle)
        local x = int16_lsb(handle)
        --# convert little-endian to big-endian
        local y = 0
        y = y + bit.blshift(bit.band(x, 0x00FF), 8)
        y = y + bit.brshift(bit.band(x, 0xFF00), 8)
        return y
    end

    local function int32_lsb(handle)
        return bit.bor(bit.blshift(int16_lsb(handle), 16), int16_lsb(handle))
    end

    local function int32_msb(handle)
        local x = int32_lsb(handle)
        --# convert little-endian to big-endian
        local y = 0
        y = y + bit.blshift(bit.band(x, 0x000000FF), 24)
        y = y + bit.brshift(bit.band(x, 0xFF000000), 24)
        y = y + bit.blshift(bit.band(x, 0x0000FF00), 8)
        y = y + bit.brshift(bit.band(x, 0x00FF0000), 8)
        return y
    end

    local function nbs_string(handle)
        local str = ""
        for i = 1, int32_msb(handle) do
            str = str..string.char(byte_lsb(handle))
        end
        return str
    end

    local function readNbs(path)
        assert(fs.exists(path), "Note NBS loading error: File \""..path.."\" not found. Did you forget to specify the containing folder?", 0)
        assert(not fs.isDir(path), "Note NBS loading error: Specified file \""..path.."\" is actually a folder.", 0)
        local handle = fs.open(path, "rb")

        song = { notes = {}; }

        --# NBS format found on http://www.stuffbydavid.com/nbs
        --# Part 1: Header
        song.length = int16_msb(handle)
        int16_msb(handle) --# layers, this is Note Block Studio meta-data
        song.name = nbs_string(handle)
        song.author = nbs_string(handle)
        song.originalauthor = nbs_string(handle)
        song.description = nbs_string(handle)
        song.realTempo = int16_msb(handle)/100
        song.tempo = song.realTempo < 20 and (20%song.realTempo == 0 and song.realTempo or math.floor(2000/math.floor(20/song.realTempo+0.5))/100) or 20

        byte_lsb(handle) --# auto-saving has been enabled (0 or 1)
        byte_lsb(handle) --# The amount of minutes between each auto-save (if it has been enabled) (1-60)
        byte_lsb(handle) --# The time signature of the song. If this is 3, then the signature is 3/4. Default is 4. This value ranges from 2-8
        int32_msb(handle) --# The amount of minutes spent on the project
        int32_msb(handle) --# The amount of times the user has left clicked
        int32_msb(handle) --# The amount of times the user has right clicked
        int32_msb(handle) --# The amount of times the user have added a block
        int32_msb(handle) --# The amount of times the user have removed a block
        nbs_string(handle) --# If the song has been imported from a .mid or .schematic file, that file name is stored here (Only the name of the file, not the path)

        --# Part 2: Note Blocks
        local maxPitch = 24
        local notes = song.notes
        local tick = -1
        local jumps = 0
        while true do
            jumps = int16_msb(handle)
            if jumps == 0 then
                break
            end
            tick = tick + jumps
            local layer = -1
            while true do
                jumps = int16_msb(handle)
                if jumps == 0 then
                    break
                end
                layer = layer + jumps
                local inst = byte_lsb(handle)
                local key = byte_lsb(handle)
                --
                notes[tick] = notes[tick] or {}
                table.insert(notes[tick], {inst = translate[inst]; pitch = math.max((key-33)%maxPitch,0)})
            end
        end

        --[[
        the rest of the file is useless to us
        it's specific to Note Block Studio and
        even so is still optional
        --]]

        handle.close()
    end

    function songEngine(targetSong)
        assert(ironnote[1], "Note songEngine failure: No Iron Note Blocks assigned.", 0)

        local tTick, curPeripheral, delay, notes = os.startTimer(0.1), 1

        if targetSong then os.queueEvent("musicPlay",targetSong) end

        while true do
            local e, someTime = { os.pullEvent() }, os.clock()

            if e[1] == "timer" and e[2] == tTick and song and not paused then
                if notes[cTick] then
                    local curMaxNotes, nowPlaying = (song.tempo == 20 and math.floor(MAX_INSTRUMENTS_PER_NOTE_BLOCK/2) or MAX_INSTRUMENTS_PER_NOTE_BLOCK) * #ironnote, 0
                    for _,note in pairs(notes[cTick]) do
                        ironnote[curPeripheral].playNote(note.inst, note.pitch)
                        curPeripheral = (curPeripheral == #ironnote) and 1 or (curPeripheral + 1)
                        nowPlaying = nowPlaying + 1
                        if nowPlaying == curMaxNotes then break end
                    end
                end

                cTick = cTick + 1

                if cTick > song.length then
                    song = nil
                    notes = nil
                    cTick = nil
                    os.queueEvent("musicFinished")
                end

                tTick = os.startTimer(delay + (someTime - os.clock()))  -- The redundant brackets shouldn't be needed, but it doesn't work without them??

            elseif e[1] == "musicPause" then
                paused = true
            elseif e[1] == "musicResume" then
                paused = false
                tTick = os.startTimer(0.1)
            elseif e[1] == "musicSkipTo" then
                cTick = e[2]
            elseif e[1] == "musicPlay" then
                readNbs(e[2])
                notes = song.notes
                cTick = 0
                tTick = os.startTimer(0.1)
                paused = false
                delay = math.floor(100 / song.tempo) / 100
                os.queueEvent("newTrack")
            end
        end
    end

    function playSong(targetSong)
        parallel.waitForAny(function () songEngine(targetSong) end, function () os.pullEvent("musicFinished") end)
    end
else
    -- -----------------------
    -- Run as jukebox:
    -- ------------------------------------------------------------

    -- Ignore everything below this point if you're only interested
    -- in the API, unless you want to see example usage.

    -- ------------------------------------------------------------

    sleep(0)  -- 'cause ComputerCraft is buggy.

    local xSize, ySize = term.getSize()

    if xSize < 50 then
        print("Wider display required!\n")
        error()
    end

    if ySize < 7 then
        print("Taller display required!\n")
        error()
    end

    local startDir = shell.resolve(".")

    os.loadAPI(shell.getRunningProgram())

    local playmode, lastSong, marqueePos, myEvent, bump, marquee = 0, {}, 1
    local cursor = {{">>  ","  <<"},{"> > "," < <"},{" >> "," << "},{"> > "," < <"}}
    local logo = {{"| |","|\\|","| |"},{"+-+","| |","+-+"},{"---"," | "," | "},{"+--","|- ","+--"}}
    local buttons = {{"| /|","|< |","| \\|"},{"|\\ |","| >|","|/ |"},{"| |","| |","| |"},{"|\\ ","| >","|/ "}}

    -- Returns whether a click was performed at a given location.
    -- If one parameter is passed, it checks to see if y is [1].
    -- If two parameters are passed, it checks to see if x is [1] and y is [2].
    -- If three parameters are passed, it checks to see if x is between [1]/[2] (non-inclusive) and y is [3].
    -- If four paramaters are passed, it checks to see if x is between [1]/[2] and y is between [3]/[4] (non-inclusive).
    local function clickedAt(...)
        if myEvent[1] ~= "mouse_click" then return false end
        if #arg == 1 then return (arg[1] == myEvent[4])
        elseif #arg == 2 then return (myEvent[3] == arg[1] and myEvent[4] == arg[2])
        elseif #arg == 3 then return (myEvent[3] > arg[1] and myEvent[3] < arg[2] and myEvent[4] == arg[3])
        else return (myEvent[3] > arg[1] and myEvent[3] < arg[2] and myEvent[4] > arg[3] and myEvent[4] < arg[4]) end
    end

    -- Returns whether one of a given set of keys was pressed.
    local function pressedKey(...)
        if myEvent[1] ~= "key" then return false end
        for i=1,#arg do if arg[i] == myEvent[2] then return true end end
        return false
    end

    local function drawPlaymode()
        term.setBackgroundColour(term.isColour() and colours.lightGrey or colours.black)
        term.setTextColour(term.isColour() and colours.black or colours.white)

        term.setCursorPos(bump+34, 1)
        term.write("[R]epeat    ( )")
        term.setCursorPos(bump+34, 2)
        term.write("Auto-[N]ext ( )")
        term.setCursorPos(bump+34, 3)
        term.write("[M]ix       ( )")

        if playmode ~= 0 then
            term.setTextColour(term.isColour() and colours.blue or colours.white)
            term.setCursorPos(bump+47, playmode)
            term.write("O")
        end
    end

    local function drawInterface()
        if term.isColour() then
            -- Header / footer.
            term.setBackgroundColour(colours.grey)
            for i = 1, 3 do
                term.setCursorPos(1,i)
                term.clearLine()
                term.setCursorPos(1,ySize-i+1)
                term.clearLine()
            end

            -- Quit button.
            term.setTextColour(colours.white)
            term.setBackgroundColour(colours.red)
            term.setCursorPos(xSize,1)
            term.write("X")
        end

        -- Note logo.
        term.setTextColour(term.isColour() and colours.blue or colours.white)
        term.setBackgroundColour(term.isColour() and colours.lightGrey or colours.black)
        for i = 1, 4 do for j = 1, 3 do
            term.setCursorPos(bump + (i - 1) * 4, j)
            term.write(logo[i][j])
        end end

        -- Skip back / forward buttons.
        term.setTextColour(term.isColour() and colours.lightBlue or colours.white)
        term.setBackgroundColour(term.isColour() and colours.grey or colours.black)
        for j = 0, 1 do for i = 1, 3 do
            term.setCursorPos(bump + 17 + j * 11, i)
            term.write(buttons[j+1][i])
        end end

        -- Progress bar.
        term.setCursorPos(2,ySize-1)
        term.setTextColour(term.isColour() and colours.black or colours.white)
        term.setBackgroundColour(term.isColour() and colours.lightGrey or colours.black)
        term.write("|"..string.rep("=",xSize-4).."|")

        drawPlaymode()
    end

    local function startSong(newSong)
        if #lastSong == 32 then lastSong[32] = nil end
        table.insert(lastSong,1,newSong)
        os.queueEvent("musicPlay",newSong)
        marquee = nil
        marqueePos = 1
    end

    local function noteMenu()
        local lastPauseState = "maybe"
        bump = math.floor((xSize - 49) / 2) + 1
        drawInterface()

        while true do
            local displayList, position, lastPosition, animationTimer, curCount, gapTimer, lastProgress = {}, 1, 0, os.startTimer(0), 1
            if #shell.resolve(".") > 0 then displayList[1] = ".." end

            do
                local fullList = fs.list(shell.resolve("."))
                table.sort(fullList, function (a, b) return string.lower(a) < string.lower(b) end)
                for i = 1, #fullList do if fs.isDir(shell.resolve(fullList[i])) then displayList[#displayList + 1] = fullList[i] end end
                for i = 1, #fullList do if fullList[i]:sub(#fullList[i]-3):lower() == ".nbs" then displayList[#displayList + 1] = fs.getName(fullList[i]) end end
            end

            while true do
                myEvent = {os.pullEvent()}

                -- Track animations (bouncing, function (a, b) return string.lower(a) < string.lower(b) end cursor + scrolling marquee).
                if myEvent[1] == "timer" and myEvent[2] == animationTimer then
                    if marquee then marqueePos = marqueePos == #marquee and 1 or (marqueePos + 1) end
                    curCount = curCount == 4 and 1 or (curCount + 1)
                    animationTimer = os.startTimer(0.5)
                    myEvent[1] = "cabbage"

                    -- Queue a new song to start playing, based on the playmode toggles (or if the user clicked the skip-ahead button).
                elseif (myEvent[1] == "timer" and myEvent[2] == gapTimer and not note.isPlaying()) or (pressedKey(keys.d,keys.right) or clickedAt(bump+27,bump+32,0,4)) then
                    if playmode == 1 then
                        os.queueEvent("musicPlay",lastSong[1])
                    elseif (playmode == 2 or (playmode == 0 and myEvent[1] ~= "timer")) and not fs.isDir(shell.resolve(displayList[#displayList])) then
                        if shell.resolve(displayList[position]) == lastSong[1] or fs.isDir(shell.resolve(displayList[position])) then repeat
                            position = position + 1
                            if position > #displayList then position = 1 end
                        until not fs.isDir(shell.resolve(displayList[position])) end

                        startSong(shell.resolve(displayList[position]))
                    elseif playmode == 3 and not fs.isDir(shell.resolve(displayList[#displayList])) then
                        repeat position = math.random(#displayList) until not fs.isDir(shell.resolve(displayList[position]))
                        startSong(shell.resolve(displayList[position]))
                    end

                    gapTimer = nil
                    myEvent[1] = "cabbage"

                elseif myEvent[1] ~= "timer" then   -- Special consideration, bearing in mind that the songEngine is spamming ten such events a second...
                    -- Move down the list.
                    if pressedKey(keys.down,keys.s) or (myEvent[1] == "mouse_scroll" and myEvent[2] == 1) then
                        position = position == #displayList and 1 or (position + 1)

                        -- Move up the list.
                    elseif pressedKey(keys.up,keys.w) or (myEvent[1] == "mouse_scroll" and myEvent[2] == -1) then
                        position = position == 1 and #displayList or (position - 1)

                        -- Start a new song.
                    elseif pressedKey(keys.enter, keys.space) or (clickedAt(bump+22,bump+26,0,4) and not note.isPlaying()) or clickedAt(math.floor(ySize / 2) + 1) then
                        if fs.isDir(shell.resolve(displayList[position])) then
                            shell.setDir(shell.resolve(displayList[position]))
                            break
                        else startSong(shell.resolve(displayList[position])) end

                        -- User clicked somewhere on the file list; move that entry to the currently-selected position.
                    elseif clickedAt(0, xSize + 1, 3, ySize - 2) then
                        position = position + myEvent[4] - math.floor(ySize / 2) - 1
                        position = position > #displayList and #displayList or position
                        position = position < 1 and 1 or position

                        -- Respond to a screen-resize; triggers a full display redraw.
                    elseif myEvent[1] == "term_resize" then
                        xSize, ySize = term.getSize()
                        bump = math.floor((xSize - 49) / 2) + 1
                        lastPosition = 0
                        drawInterface()
                        animationTimer = os.startTimer(0)
                        lastPauseState = "maybe"

                        -- Quit.
                    elseif pressedKey(keys.q, keys.x, keys.t) or clickedAt(xSize, 1) then
                        if myEvent[1] == "key" then os.pullEvent("char") end
                        os.unloadAPI("note")
                        term.setTextColour(colours.white)
                        term.setBackgroundColour(colours.black)
                        term.clear()
                        term.setCursorPos(1,1)
                        print("Thanks for using the Note NBS player!\n")
                        shell.setDir(startDir)
                        error()

                        -- Toggle repeat mode.
                    elseif pressedKey(keys.r) or clickedAt(bump + 33, bump + 49, 1) then
                        playmode = playmode == 1 and 0 or 1
                        drawPlaymode()

                        -- Toggle auto-next mode.
                    elseif pressedKey(keys.n) or clickedAt(bump + 33, bump + 49, 2) then
                        playmode = playmode == 2 and 0 or 2
                        drawPlaymode()

                        -- Toggle mix (shuffle) mode.
                    elseif pressedKey(keys.m) or clickedAt(bump + 33, bump + 49, 3) then
                        playmode = playmode == 3 and 0 or 3
                        drawPlaymode()

                        -- Music finished; wait a second or two before responding.
                    elseif myEvent[1] == "musicFinished" then
                        gapTimer = os.startTimer(2)
                        lastPauseState = "maybe"
                        marquee = ""

                        -- Skip back to start of the song (or to the previous song, if the current song just started).
                    elseif pressedKey(keys.a,keys.left) or clickedAt(bump+16,bump+21,0,4) then
                        if note.isPlaying() and note.getSongPositionSeconds() > 3 then
                            os.queueEvent("musicSkipTo",0)
                            os.queueEvent("musicResume")
                        elseif #lastSong > 1 then
                            table.remove(lastSong,1)
                            startSong(table.remove(lastSong,1))
                        end

                        -- Toggle pause/resume.
                    elseif note.isPlaying() and (pressedKey(keys.p) or clickedAt(bump+22,bump+26,0,4)) then
                        if note.isPaused() then os.queueEvent("musicResume") else os.queueEvent("musicPause") end

                        -- Tracking bar clicked.
                    elseif note.isPlaying() and clickedAt(1, xSize, ySize - 1) then
                        os.queueEvent("musicSkipTo",math.floor(note.getSongLength()*(myEvent[3]-1)/(xSize-2)))

                        -- Song engine just initiated a new track.
                    elseif myEvent[1] == "newTrack" then
                        marquee = " [Title: "
                        if note.getSongName() ~= "" then marquee = marquee..note.getSongName().."]" else marquee = marquee..fs.getName(lastSong[1]).."]" end
                        if note.getSongArtist() ~= "" then marquee = marquee.." [Artist: "..note.getSongArtist().."]" end
                        if note.getSongAuthor() ~= "" then marquee = marquee.." [NBS Author: "..note.getSongAuthor().."]" end
                        marquee = marquee.." [Tempo: "..note.getSongTempo()
                        marquee = marquee..(note.getSongTempo() == note.getRealSongTempo() and "]" or (" (NBS: "..note.getRealSongTempo()..")]"))
                        if note.getSongDescription() ~= "" then marquee = marquee.." [Description: "..note.getSongDescription().."]" end
                        lastPauseState = "maybe"
                    end
                end

                -- Play / pause button.
                if lastPauseState ~= note.isPaused() then
                    term.setTextColour(term.isColour() and colours.lightBlue or colours.white)
                    term.setBackgroundColour(term.isColour() and colours.grey or colours.black)
                    for i=1,3 do
                        term.setCursorPos(bump + 23,i)
                        term.write(buttons[(note.isPlaying() and not note.isPaused()) and 3 or 4][i])
                    end
                    lastPauseState = note.isPaused()
                end

                -- Update other screen stuff.
                if myEvent[1] ~= "timer" then
                    term.setTextColour(term.isColour() and colours.black or colours.white)
                    term.setBackgroundColour(term.isColour() and colours.lightGrey or colours.black)

                    -- Clear old progress bar position.
                    if lastProgress then
                        term.setCursorPos(lastProgress,ySize-1)
                        term.write((lastProgress == 2 or lastProgress == xSize - 1) and "|" or "=")
                        lastProgress = nil
                    end

                    -- Song timers.
                    if note.isPlaying() then
                        term.setCursorPos(xSize-5,ySize-2)
                        if term.isColour() and note.getSongTempo() ~= note.getRealSongTempo() then term.setTextColour(colours.red) end

                        local mins = tostring(math.min(99,math.floor(note.getSongSeconds()/60)))
                        local secs = tostring(math.floor(note.getSongSeconds()%60))
                        term.write((#mins > 1 and "" or "0")..mins..":"..(#secs > 1 and "" or "0")..secs)

                        term.setCursorPos(2,ySize-2)
                        if note.isPaused() and bit.band(curCount,1) == 1 then
                            term.write("     ")
                        else
                            mins = tostring(math.min(99,math.floor(note.getSongPositionSeconds()/60)))
                            secs = tostring(math.floor(note.getSongPositionSeconds()%60))
                            term.write((#mins > 1 and "" or "0")..mins..":"..(#secs > 1 and "" or "0")..secs)
                        end

                        -- Progress bar position.
                        term.setTextColour(term.isColour() and colours.blue or colours.white)
                        term.setBackgroundColour(colours.black)
                        lastProgress = 2+math.floor(((xSize-3) * note.getSongPosition() / note.getSongLength()))
                        term.setCursorPos(lastProgress,ySize-1)
                        term.write("O")
                    else
                        term.setCursorPos(2,ySize-2)
                        term.write("00:00")
                        term.setCursorPos(xSize-5,ySize-2)
                        term.write("00:00")
                    end

                    -- Scrolling marquee.
                    if marquee then
                        term.setTextColour(term.isColour() and colours.black or colours.white)
                        term.setBackgroundColour(term.isColour() and colours.grey or colours.black)
                        term.setCursorPos(1,ySize)

                        if marquee == "" then
                            term.clearLine()
                            marquee = nil
                        else
                            local thisLine = marquee:sub(marqueePos,marqueePos+xSize-1)
                            while #thisLine < xSize do thisLine = thisLine..marquee:sub(1,xSize-#thisLine) end
                            term.write(thisLine)
                        end
                    end

                    -- File list.
                    term.setBackgroundColour(colours.black)
                    for y = position == lastPosition and (math.floor(ySize / 2)+1) or 4, position == lastPosition and (math.floor(ySize / 2)+1) or (ySize - 3) do
                        local thisLine = y + position - math.floor(ySize / 2) - 1

                        if displayList[thisLine] then
                            local thisString = displayList[thisLine]
                            thisString = fs.isDir(shell.resolve(thisString)) and "["..thisString.."]" or thisString:sub(1,#thisString-4)

                            if thisLine == position then
                                term.setCursorPos(math.floor((xSize - #thisString - 8) / 2)+1, y)
                                term.clearLine()
                                term.setTextColour(term.isColour() and colours.cyan or colours.black)
                                term.write(cursor[curCount][1])
                                term.setTextColour(term.isColour() and colours.blue or colours.white)
                                term.write(thisString)
                                term.setTextColour(term.isColour() and colours.cyan or colours.black)
                                term.write(cursor[curCount][2])
                            else
                                term.setCursorPos(math.floor((xSize - #thisString) / 2)+1, y)
                                term.clearLine()

                                if y == 4 or y == ySize - 3 then
                                    term.setTextColour(colours.black)
                                elseif y == 5 or y == ySize - 4 then
                                    term.setTextColour(term.isColour() and colours.grey or colours.black)
                                elseif y == 6 or y == ySize - 5 then
                                    term.setTextColour(term.isColour() and colours.lightGrey or colours.white)
                                else term.setTextColour(colours.white) end

                                term.write(thisString)
                            end
                        else
                            term.setCursorPos(1,y)
                            term.clearLine()
                        end
                    end

                    lastPosition = position
                end
            end
        end
    end

    do local args = {...}
    for i=1,#args do if args[i]:lower() == "-r" then
        playmode = 1
    elseif args[i]:lower() == "-n" then
        playmode = 2
    elseif args[i]:lower() == "-m" then
        playmode = 3
    elseif fs.isDir(shell.resolve(args[i])) then
        shell.setDir(shell.resolve(args[i]))
    elseif fs.isDir(args[i]) then
        shell.setDir(args[i])
    elseif fs.exists(shell.resolve(args[i])) then
        local filePath = shell.resolve(args[i])
        shell.setDir(fs.getDir(filePath))
        startSong(filePath)
    elseif fs.exists(shell.resolve(args[i]..".nbs")) then
        local filePath = shell.resolve(args[i]..".nbs")
        shell.setDir(fs.getDir(filePath))
        startSong(filePath)
    elseif fs.exists(args[i]) then
        shell.setDir(fs.getDir(args[i]))
        startSong(args[i])
    elseif fs.exists(args[i]..".nbs") then
        shell.setDir(fs.getDir(args[i]))
        startSong(args[i]..".nbs")
    end end end

    if playmode > 1 then os.queueEvent("musicFinished") end

    term.setBackgroundColour(colours.black)
    term.clear()

    parallel.waitForAny(note.songEngine, noteMenu)
end