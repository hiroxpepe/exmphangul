
test("normal test", function() {
    
    ok(true, "if true is green.");
    equals(1, "1", "1=='1'");
    same(1, 1, "1===1");
    
});

test('Hello, QUnit! - ok assertions test', function() {
    
    var testString = 'testValue';
    ok(5 < testString.length, 'testString is greater than 5 characters.');
    
});

test('Hello, QUnit! - equals assertions test', function() {
    
    var actualString = 'testValue';
    var expectedString = 'testValue';
    equals(actualString, expectedString, 'actualString and expectedString is the same value.');

    var actualArray = new Array(1,2,3,4,5);
    var expectedArray = actualArray;
    equals(actualArray, expectedArray, 'actualArray and expectedArray is the same array.');

});
