
test("LetterIdArrayTransformer test a", function() {

    var instance = exmp.hangul.functor.value.LetterIdArrayTransformer;

    var obj1 = { value: "a" }
    var idObjList1 = instance.transform(obj1);
    
    var originalString1 = idObjList1[0].originalString;
    var initialId1 = idObjList1[0].initialId;
    var peakId1 = idObjList1[0].peakId;
    var finalId1 = idObjList1[0].finalId;
    var remains1 = idObjList1[0].remains;
    
    equals("a", originalString1, "a is originalString a");
    equals("11", initialId1, "a is initialId 11");
    equals("0", peakId1, "a is peakId 0");
    equals(null, finalId1, "a is finalId null");
    equals(null, remains1, "a is remains null");

});

test("LetterIdArrayTransformer test an", function() {

    var instance = exmp.hangul.functor.value.LetterIdArrayTransformer;
    
    var obj = { value: "an" }
    var idObjList = instance.transform(obj);
    
    var originalString1 = idObjList[0].originalString;
    var initialId1 = idObjList[0].initialId;
    var peakId1 = idObjList[0].peakId;
    var finalId1 = idObjList[0].finalId;
    var remains1 = idObjList[0].remains;
    
    equals("an", originalString1, "an is originalString an");
    equals("11", initialId1, "an is initialId 11");
    equals("0", peakId1, "an is peakId 0");
    equals("4", finalId1, "an is finalId 4");
    equals(null, remains1, "an is remains null");

});

test("LetterIdArrayTransformer test nyeong", function() {

    var instance = exmp.hangul.functor.value.LetterIdArrayTransformer;
    
    var obj = { value: "nyeong" }
    var idObjList = instance.transform(obj);
    
    // nyeong
    var originalString1 = idObjList[0].originalString;
    var initialId1 = idObjList[0].initialId;
    var peakId1 = idObjList[0].peakId;
    var finalId1 = idObjList[0].finalId;
    var remains1 = idObjList[0].remains;
    
    equals("nyeong", originalString1, "nyeong is originalString nyeong");
    equals("2", initialId1, "nyeong is initialId 2");
    equals("6", peakId1, "nyeong is peakId 6");
    equals("21", finalId1, "nyeong is finalId 21");
    equals(null, remains1, "nyeong is remains null");

});

test("LetterIdArrayTransformer test ha", function() {

    var instance = exmp.hangul.functor.value.LetterIdArrayTransformer;
    
    var obj = { value: "ha" }
    var idObjList = instance.transform(obj);
    
    // ha
    var originalString1 = idObjList[0].originalString;
    var initialId1 = idObjList[0].initialId;
    var peakId1 = idObjList[0].peakId;
    var finalId1 = idObjList[0].finalId;
    var remains1 = idObjList[0].remains;
    
    equals("ha", originalString1, "ha is originalString ha");
    equals("18", initialId1, "ha is initialId 18");
    equals("0", peakId1, "ha is peakId 0");
    equals(null, finalId1, "ha is finalId null");
    equals(null, remains1, "ha is remains null");

});

test("LetterIdArrayTransformer test se", function() {

    var instance = exmp.hangul.functor.value.LetterIdArrayTransformer;
    
    var obj = { value: "se" }
    var idObjList = instance.transform(obj);
    
    // se
    var originalString1 = idObjList[0].originalString;
    var initialId1 = idObjList[0].initialId;
    var peakId1 = idObjList[0].peakId;
    var finalId1 = idObjList[0].finalId;
    var remains1 = idObjList[0].remains;
    
    equals("se", originalString1, "se is originalString se");
    equals("9", initialId1, "se is initialId 9");
    equals("5", peakId1, "se is peakId 5");
    equals(null, finalId1, "se is finalId null");
    equals(null, remains1, "se is remains null");

});

test("LetterIdArrayTransformer test yo", function() {

    var instance = exmp.hangul.functor.value.LetterIdArrayTransformer;
    
    var obj = { value: "yo" }
    var idObjList = instance.transform(obj);
    
    // yo
    var originalString1 = idObjList[0].originalString;
    var initialId1 = idObjList[0].initialId;
    var peakId1 = idObjList[0].peakId;
    var finalId1 = idObjList[0].finalId;
    var remains1 = idObjList[0].remains;
    
    equals("yo", originalString1, "yo is originalString yo");
    equals("11", initialId1, "yo is initialId 11");
    equals("12", peakId1, "yo is peakId 12");
    equals(null, finalId1, "yo is finalId null");
    equals(null, remains1, "yo is remains null");

});