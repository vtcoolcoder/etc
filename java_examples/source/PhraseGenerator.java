import java.util.Random;


public class PhraseGenerator
{
    private static final int FAIL_EXIT = 1;
    private static final int MESSAGE_LIMIT = 1000000;
    
    private static final String INCORRECT_LIMIT = 
        "Значение лимита должно быть не меньше 1!";
    private static final String OUT_OF_LIMIT = 
        "Превышен лимит генерируемых сообщений!\n" +
        "Текущий лимит: " + MESSAGE_LIMIT + " сообщений.";
    private static final String ERROR_MESSAGE =
        "Правила использования:\n\t" +
        "java PhraseGenerator N\n\t" +
        ", где N -- любое натуральное число больше нуля.";

    private static final boolean SUBJECT = true;
    private static final boolean OBJECT = false;
    
    private static final String SPACE = " ";
    private static final String COMMA = ",";
    private static final String COLON = ":";
    private static final String SEMICOLON = ";";
    private static final String DOT = ".";
    private static final String MULTIDOT = "...";
    private static final String EXCLAMATION = "!";
    private static final String QUESTION = "?";
    
    private static final String[] ENDING_SIGNS =
    {
        DOT,
        MULTIDOT,
        EXCLAMATION,
        QUESTION
    };
    
    private static final String[] NOUNS =
    {
        "кошка",
        "собака",
        "лиса",
        "белка",
        "заяц",
        "волк",
        "медведь",
        "петух",
        "голубь",
        "свинья",
        "лось",
        "ящерица",
        "обезьяна",
        "пастух",
        "король",
        "император",
        "рыцарь",
        "принц",
        "королева",
        "принцесса",
        "императрица",
        "князь",
        "царь",
        "царевна",
        "жаба",
        "рыба",
        "цапля",
        "конь",
        "лошадка",
        "утка",
        "селезень",
        "помидор",
        "огурец",
        "перец",
        "табак"
    };
    
    private static final String[] ADJECTIVES =
    {
        "красив",
        "страшн",
        "чист",
        "грязн",
        "светл",
        "тёмн",
        "гениальн",
        "заботлив",
        "ласков",
        "прикольн",
        "здоров",
        "больш",
        "маленьк",
        "восхитительн",
        "всепоглощающ",
        "равнодушн",
        "талантлив",
        "упорн",
        "великодушн",
        "развлекательн",
        "глубок",
        "мечтательн",
        "прозаичн",
        "роскошн",
        "горд",
        "дик",
        "парадоксальн",
        "относительн",
        "абсолютн",
        "разумн",
        "мягк",
        "жёстк",
        "толков",
        "инфантильн",
        "велик",
        "хитр",
        "трансцендентн"
    };
    
    private static final String[] VERBS =
    {
        "видит",
        "слышит",
        "обоняет",
        "осязает",
        "ощущает",
        "хвалит",
        "уважает",
        "любит",
        "ненавидит",
        "игнорирует",
        "соблюдает",
        "презирает",
        "предвосхищает",
        "сновидит",
        "готовит",
        "пьёт",
        "ест",
        "фотографирует",
        "облизывает",
        "отрицает",
        "проклинает",
        "воплощает",
        "танцует",
        "хочет",
        "ремонтирует",
        "ожидает",
        "провожает",
        "кричит",
        "шепчет",
        "говорит",
        "планирует",
        "деконструирует"
    };
    
    private static final String[] ADVERBS =
    {
        "хорошо",
        "плохо",
        "удовлетворительно",
        "нейтрально",
        "изящно",
        "качественно",
        "высоко",
        "низко",
        "выгодно",
        "убыточно",
        "щепетильно",
        "предвзято",
        "безапелляционно",
        "трусливо",
        "фатально",
        "продуктивно",
        "насыщенно",
        "слабо",
        "сильно",
        "лаконично",
        "грозно",
        "убедительно",
        "феерично",
        "успешно",
        "насмешливо",
        "логично",
        "рационально",
        "иррационально",
        "мужественно",
        "дипломатично",
        "бережно",
        "тривиально",
        "сумбурно",
        "редко",
        "часто",
        "явно",
        "тайно",
        "научно",
        "просто",
        "сложно",
        "аналогично",
        "невозмутимо",
        "эротично"
    };
    
    private static final String[] OBJECTS =
    {
        "дерево",
        "траву",
        "воду",
        "небо",
        "планету",
        "одеяло",
        "сыр",
        "колбасу",
        "эчпочмак",
        "транзистор",
        "архетип",
        "ковёр",
        "пиво",
        "вино",
        "водку",
        "ром",
        "ликёр",
        "виски",
        "кровать",
        "стол",
        "шкаф",
        "диван",
        "эспандер",
        "гирю",
        "гантель",
        "плакат",
        "лопату",
        "пирог",
        "печеньку",
        "конфетку",
        "творог",
        "берёзу",
        "сосну",
        "гриб",
        "мухомор",
        "костюм",
        "дупло"
    };   
    
    
    private String adjAdverb;
    private String adjective;
    private String noun;
    private String vrbAdverb;
    private String verb;
    private String objAdjAdverb;
    private String objAdjective;
    private String object;
    private String endingSign;
    
    private StringBuilder result = new StringBuilder();
    
    
    public static void main(String[] args)
    {   
        if (args.length == 0)
        {
            new PhraseGenerator();
        }
        else if (args.length > 1)
        {
            showErrorMessage(ERROR_MESSAGE);
        }
        
        try
        {
            System.out.print(
                new PhraseGenerator(
                    getValidLimit(Long.parseLong(args[0]))));
        }   
        catch (NumberFormatException e)
        {
            showErrorMessage(ERROR_MESSAGE);
        }
    }
    
    
    private static int getValidLimit(long limit)
    {
        if (limit < 1)
        {
            showErrorMessage(INCORRECT_LIMIT);
        }
        else if (limit > MESSAGE_LIMIT || 
                 limit > Integer.MAX_VALUE)
        {
            showErrorMessage(OUT_OF_LIMIT);
        }
        
        return (int)limit;
    }
    
    
    private static void showErrorMessage(String message)
    {
        System.err.println(message);
        System.exit(FAIL_EXIT);
    }
    
    
    public PhraseGenerator()
    {
        runBruteForce();
    }
    
    
    public PhraseGenerator(int limit)
    {
        runRandomPhraseGenerator(limit);
    }
    
    
    public String toString()
    {
        return result.toString();
    }
    
      
    private void runRandomPhraseGenerator(int limit)
    {
        for (int i = 1; i <= limit; i++)
        {
            String currentPhrase = getRandomPhrase();
            //System.out.println(currentPhrase);
            result.append(currentPhrase + "\n");           
        }
    }
    
    
    private String getRandomPhrase()
    {
        adjAdverb = getRandomItem(ADVERBS);
        adjective = getRandomItem(ADJECTIVES);
        noun = getRandomItem(NOUNS);
        vrbAdverb = getRandomItem(ADVERBS);
        verb = getRandomItem(VERBS);
        objAdjAdverb = getRandomItem(ADVERBS);
        objAdjective = getRandomItem(ADJECTIVES);
        object = getRandomItem(OBJECTS);
        endingSign = getRandomItem(ENDING_SIGNS);
        
        return getResult();
    }
    
    
    private String getRandomItem(String[] container)
    {
        return container[new Random().nextInt(container.length)];
    }
    
    
    private void runBruteForce()
    {
        for (String anAdjAdverb : ADVERBS)
        {
            for (String anAdjective : ADJECTIVES)
            {
                for (String aNoun : NOUNS)
                {
                    for (String aVrbAdverb : ADVERBS)
                    {
                        for (String aVerb : VERBS)
                        {   
                            for (String anObjAdjAdverb : ADVERBS)
                            {
                                for (String anObjAdjective : ADJECTIVES)
                                {
                                    for (String anObject : OBJECTS)
                                    {
                                        for (String anEndingSign : ENDING_SIGNS)
                                        {
                                            adjAdverb = anAdjAdverb;
                                            adjective = anAdjective;
                                            noun = aNoun;
                                            vrbAdverb = aVrbAdverb;
                                            verb = aVerb;
                                            objAdjAdverb = anObjAdjAdverb;
                                            objAdjective = anObjAdjective;
                                            object = anObject;
                                            endingSign = anEndingSign;
                                        
                                            String currentPhrase = getResult();
                                            System.out.println(currentPhrase);
                                            //result.append(currentPhrase + "\n");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    
    private String getResult()
    {
        StringBuilder result = new StringBuilder();
        
        result.append(adjAdverb);
        result.append(SPACE);                
        result.append(getValidAdjective(SUBJECT, adjective, noun));
        result.append(SPACE);
        result.append(noun);
        result.append(SPACE);
        result.append(vrbAdverb);
        result.append(SPACE);
        result.append(verb);
        result.append(SPACE);
        result.append(objAdjAdverb);
        result.append(SPACE);
        result.append(getValidAdjective(OBJECT, objAdjective, object));
        result.append(SPACE);
        result.append(object);
        result.append(endingSign);
        
        return getFirstLetterToUpCase(result);
    }
    
    
    private String getFirstLetterToUpCase(StringBuilder item)
    {
        String before = ("" + item.charAt(0)).toUpperCase();
        String after = item.substring(1);
        item.replace(0, item.length(), before + after);
        
        return "" + item;
    } 
    
    
    private String getValidAdjective(boolean isNounSubject,
                                     String adjective, String noun)
    {  
        String nounLastLetter = getLastLetters(noun, 1);
        String nounThreeLastLetters = getLastLetters(noun, 3);
        
        String adjectiveLastLetter = getLastLetters(adjective, 1);
        String adjectiveTwoLastLetters = getLastLetters(adjective, 2);
        
        if (isFemale(nounLastLetter) ||
            isFemale(nounThreeLastLetters))
        {
            return getFemaleAdjective(isNounSubject, adjective, 
                                      adjectiveTwoLastLetters);
        }
        else if(isMiddle(nounLastLetter))
        {
            return getMiddleAdjective(adjective, adjectiveTwoLastLetters,
                                      adjectiveLastLetter);
        }
        else
        {               
            return getMaleAdjective(adjective, adjectiveLastLetter,
                                    adjectiveTwoLastLetters);
        }
    }
    
    
    private String getFemaleAdjective(boolean isNounSubject,
                                      String adjective,
                                      String adjectiveTwoLastLetters)
    {
        if (isMatchLastLetters(adjectiveTwoLastLetters, "ин"))
        {
            return adjective + (isNounSubject ? "яя" : "юю");
        }
        else
        {
            return adjective + (isNounSubject ? "ая" : "ую");
        }           
    }
    
    
    private String getMiddleAdjective(String adjective, 
                                      String adjectiveTwoLastLetters, 
                                      String adjectiveLastLetter)
    {
        if (isMatchLastLetters(adjectiveTwoLastLetters, "ин") ||
            isMatchLastLetters(adjectiveLastLetter, "щ"))
        {
            return adjective + "ее";
        }
        else
        {
            return adjective + "ое";
        }
    }
    
    
    private String getMaleAdjective(String adjective, 
                                    String adjectiveLastLetter,
                                    String adjectiveTwoLastLetters)
    {
        if (isMatchLastLetters(adjectiveLastLetter, "ш"))
        {
            return adjective + "ой";
        }
        else if (isMatchLastLetters(adjectiveLastLetter, "к") ||
                 isMatchLastLetters(adjectiveLastLetter, "щ") ||
                 isMatchLastLetters(adjectiveTwoLastLetters, "ин"))
        {
            return adjective + "ий";
        }
        else
        {
            return adjective + "ый";
        }
    }
    
    
    private String getLastLetters(String item, int howMany)
    {
        int length = item.length();
        return item.substring(length - howMany, length);
    }
    
    
    private boolean isMatchLastLetters(String item, String letter)
    {
        return item.equals(letter);
    }
    
    
    private boolean isFemale(String item)
    {
        return item.equals("а") || 
               item.equals("я") || 
               item.equals("у") ||
               item.equals("ю") ||
               item.equals("ель") ||
               item.equals("ать");
    }
    
    
    private boolean isMiddle(String item)
    {
        return item.equals("о");
    }
}
