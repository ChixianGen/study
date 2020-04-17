Rectangle = {
    area = 0,
    length = 0,
    breadth = 0
}

-- 派生类的方法 new
function Rectangle:new (o, length, breadth)
    o = o or {}
    setmetatable(o, self)
    self.__index = self
    self.length = length or 8
    self.breadth = breadth or 0
    self.area = self.length * self.breadth;
    return o
end

-- 派生类的方法 printArea
function Rectangle:printArea ()
    print("area", self.area)
end

local r = Rectangle:new(nil,nil,20)
print(r.length)
print(r.breadth)
print(r:printArea())