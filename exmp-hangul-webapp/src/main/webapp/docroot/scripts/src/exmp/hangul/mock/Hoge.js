exmp.hangul.mock.Hoge = function(name, age) {
    this.name = name;
    this.age = age;
}
exmp.hangul.mock.Hoge.prototype = {
    say : function () {
        window.alert("my name " + this.name + " age is " + this.age);
    }
}