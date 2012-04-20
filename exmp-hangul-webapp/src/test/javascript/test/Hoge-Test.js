

test("Hoge test", function() {
    
    var hoge1 = new exmp.hangul.mock.Hoge("hoge", 38);
    hoge1.say();
    
    var hoge2 = new exmp.hangul.mock.Hoge("piyo", 15);
    hoge2.say();
    
	ok(hoge1.name == "hoge", "name is hoge");
    equals("hoge", hoge1.name, "name is hoge");
    equals(38, hoge1.age, "age == 38");
	same(38, hoge1.age, "age === 38");
    
	ok(hoge2.name == "piyo", "name is piyo");
    equals("piyo", hoge2.name, "name is piyo");
    equals(15, hoge2.age, "age == 15");
	same(15, hoge2.age, "age === 15");
    
});

test("normal test", function() {
	ok(true, "trueならGreen");
	equals(1, "1", "1=='1'");
	same(1, 1, "1===1");
});

test('Hello, QUnit! - ok assertions test', function() {
    var testString = 'testValue';
    ok( 5 < testString.length , 'testStringが5文字を超えていること');
});

test('Hello, QUnit! - equals assertions test', function() {
    var actualString = 'testValue';
    var expectedString = 'testValue';
    equals( actualString, expectedString, 'actualStringとexpectedStringがおなじ値であること');

    var actualArray = new Array(1,2,3,4,5);
    var expectedArray = actualArray;
    equals( actualArray, expectedArray, 'actualArrayとexpectedArrayがおなじ配列であること');
});
