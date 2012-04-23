
test("InputValueTransformer test a", function() {

    var instance = exmp.hangul.functor.value.InputValueTransformer;

    var obj = { value: "a" }
    var ret = instance.transform(obj);
    equals("a", ret, "a is a");
    
    instance.transform({ value: " " });

});

test("InputValueTransformer test an", function() {

    var instance = exmp.hangul.functor.value.InputValueTransformer;

    var obj1 = { value: "a" }
    var ret1 = instance.transform(obj1);
    equals("a", ret1, "a is a");
    
    var obj2 = { value: "n" }
    var ret2 = instance.transform(obj2);
    equals("an", ret2, "n is an");

    instance.transform({ value: " " });

});

test("InputValueTransformer test a' 'wo", function() {

    var instance = exmp.hangul.functor.value.InputValueTransformer;

    var obj1 = { value: "a" }
    var ret1 = instance.transform(obj1);
    equals("a", ret1, "a is a");
    
    var obj2 = { value: " " }
    var ret2 = instance.transform(obj2);
    equals(null, ret2, "' ' is null");
    
    var obj3 = { value: "w" }
    var ret3 = instance.transform(obj3);
    equals("w", ret3, "w is w");
    
    var obj4 = { value: "o" }
    var ret4 = instance.transform(obj4);
    equals("wo", ret4, "o is wo");
    
    instance.transform({ value: " " });

});