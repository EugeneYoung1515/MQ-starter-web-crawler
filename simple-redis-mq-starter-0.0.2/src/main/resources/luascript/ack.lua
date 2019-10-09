--[[
local deletefromres = redis.call('zrem',KEYS[1],ARGV[1])
local num = 0
if deletefromres == 0 then
    num = redis.call('lrem',KEYS[2],1,ARGV[1])
end
return {deletefromres,num}
--]]

local deletefromres = redis.call('zrem',KEYS[1],ARGV[1])
local num = 0
if deletefromres == 0 then
    num = redis.call('lrem',KEYS[2],1,ARGV[1])
end
redis.call('hdel',KEYS[3],ARGV[1])
return {deletefromres,num}
