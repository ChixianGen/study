
function init()
    o = {}
    setmetatable(o, s)

    s.name = 'cxg'
    s.age = 30
    return o
end

local obj = init()
print(obj.name)
print(obj.age)

--[[local a = {'a', 'b'}
print(a)

local c = setmetatable({'a', 'b'}, {
    __tostring = function(c)
    local r = 'begin'
        for i, v in ipairs(c) do
            r = r.." -- "..v
        end
        return r .. ' -- end'
    end
})
print(c)]]


--[[
function table_length(t)
    local mn = 0
    for k, v in pairs(t) do
        mn = mn + 1
    end
    return mn
end

mytable = setmetatable({ 1, 2, 3, 10 }, {
    __add = function(mytable, newtable)
        for i = 1, table_length(newtable) do
            table.insert(mytable, newtable[i])
        end
        return mytable
    end
})

secondtable = { 12, 55, 6 }

mytable = mytable + secondtable
for k, v in ipairs(mytable) do
    print(k, v)
end
]]


--[[
local mytable = setmetatable({ key1 = "value1" }, {
    __index = function(mytable, key)
        if key == "key2" then
            return "metatable value"
        else
            return "no value"
        end
    end
})

print(mytable.key1, mytable.key2, mytable.aaf)
]]

--local tb = { 'hello', 'worldela', "cxg", "lkjllkjlakjslgkjk" }
--
--function length(a, b)
--    return #a < #b
--end
--
--table.sort(tb, length)

--print(table.concat(tb, ","))


--local tb1 = { ["aa"] = "aa", ["b"] = "b", ["ccc"] = "cxg" }
--
--function newSort(a, b)
--    print(a, b)
--    return false
--end
--table.sort(tb1, newSort)
--
--for i, v in pairs(tb1) do
--    print('key', i, 'value', v)
--end
