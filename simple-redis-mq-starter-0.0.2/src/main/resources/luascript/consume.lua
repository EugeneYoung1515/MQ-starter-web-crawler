--[[
local message = redis.call('rpop', KEYS[1])

if(message ~= false) then

    redis.call('zadd', KEYS[2], ARGV[1], message)

end

return {message}
--]]

local messageId = redis.call('rpop', KEYS[1])

local message = -99

if(messageId ~= false) then

    redis.call('zadd', KEYS[2], ARGV[1], messageId)
    message = redis.call('hget',KEYS[3], messageId)

end



return {message,messageId}



