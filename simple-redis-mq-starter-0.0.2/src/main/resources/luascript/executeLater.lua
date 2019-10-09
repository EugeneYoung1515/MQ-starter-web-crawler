--[[
local id = redis.call('incr',KEYS[1])
local message = cjson.decode(KEYS[2])
message[2]['id'] = id
local message2 = cjson.encode(message)
if tonumber(message[2]['delay']) > 0 then
    redis.call('zadd',KEYS[3],ARGV[1],message2)
else
    redis.call('lpush',KEYS[4],message2)
end
return {id,message2}
--]]

local id = redis.call('incr',KEYS[1])
local message = cjson.decode(KEYS[2])
message[2]['id'] = id
local message2 = cjson.encode(message)
if tonumber(message[2]['delay']) > 0 then
    redis.call('zadd',KEYS[3],ARGV[1],id)
else
    redis.call('lpush',KEYS[4],id)
end
redis.call('hset',KEYS[5],id,message2)
--return {id,message2}
return {id}

-- 从lua拿的到spring是long 也有可能是int
