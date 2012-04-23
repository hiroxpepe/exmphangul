
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
