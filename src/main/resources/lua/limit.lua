local n = redis.call('incr', KEYS[1])
if n == 1 then
    redis.call('expire', KEYS[1], ARGV[1])
    return true
else
    if n > tonumber(ARGV[2]) then
        return false
    end
    return true
end