-----------------------------------------
--        BitNet Rednet Repeater       --
-----------------------------------------
 
-- By Jeffrey Alexander, aka Bomb Bloke.

-- Acts as repeater software for BitNet transmitters:
-- http://moarperipherals.com/index.php?title=BitNet_Mini_Antenna
-- http://moarperipherals.com/index.php?title=BitNet_Communication_Tower

-- Pretty much the same thing as the default "repeat" script:
-- http://www.computercraft.info/wiki/Repeat
-- ... though it supports BitNet devices as well. Can work with any combination
-- of modems/towers or alongside the vanilla repeat script itself.

---------------------------------------------

local modem = {peripheral.find("modem", function(name, object) object.open(rednet.CHANNEL_REPEAT) return true end)}

local antenna = {peripheral.find("bitnet_antenna", function(name, object) object.open(rednet.CHANNEL_REPEAT) return true end)}

local tower = {peripheral.find("bitnet_tower", function(name, object) if object.isTowerComplete() then object.open(rednet.CHANNEL_REPEAT) return true else return false end end)}

if #modem == 0 and #antenna == 0 and #tower == 0 then error("No devices found - can't send / receive rednet messages without one!") end

local repeated, msgID, timerID = 0, {}, {}

print(#modem.." modem"..(#modem==1 and "" or "s").." found.")
print(#antenna.." antenna"..(#antenna==1 and "" or "s").." found.")
print(#tower.." tower"..(#tower==1 and "" or "s").." found.")
print("0 messages repeated.")

for i = 1, #antenna do modem[#modem + 1] = antenna[i] end
for i = 1, #tower do modem[#modem + 1] = tower[i] end
antenna, tower = nil, nil

-- Repeat a message:
local function resend(message)
	-- Determine if the message has already been repeated by this system:
	if msgID[tostring(message.nMessageID)] then return end
	
	-- Flag the message as having passed through this system:
	msgID[tostring(message.nMessageID)] = true
	timerID[tostring(os.startTimer(30))] = tostring(message.nMessageID)
	
	-- Update the onscreen repeat counter:
	local curX, curY = term.getCursorPos()
	term.setCursorPos(1,curY-1)
	repeated = repeated + 1
	print(repeated.." messages repeated.")
	
	-- Send a copy via all attached devices:
	for i = 1, #modem do
		modem[i].transmit(message.nRecipient,    message.nSender, message)
		modem[i].transmit(rednet.CHANNEL_REPEAT, message.nSender, message)
	end
end

-- Main loop:
while true do
	local myEvent = {os.pullEvent()}
	
	-- Message arriving:
	if (myEvent[1] == "bitnet_message" or myEvent[1] == "modem_message") and type(myEvent[5]) == "table" and myEvent[5].nMessageID then
		if not myEvent[5].nSender then myEvent[5].nSender = myEvent[4] end
		resend(myEvent[5])
		
	-- Unmark a given message previously logged as repeated:
	elseif myEvent[1] == "timer" and timerID[tostring(myEvent[2])] then
		msgID[timerID[tostring(myEvent[2])]] = nil
		timerID[tostring(myEvent[2])] = nil
	
	-- Quit:
	elseif myEvent[1] == "key" and (myEvent[2] == keys.q or myEvent[2] == keys.x) then
		os.pullEvent("char")
		error()
	
	end
end
