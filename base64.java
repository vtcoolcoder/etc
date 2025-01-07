var encoder = Base64.getEncoder();
var decoder = Base64.getDecoder();

var names = List.of("Аня", "Юля", "Леся")

var encodedNames = names.stream()
        .map(String::getBytes)
        .map(encoder::encodeToString) // .map(Base64.getEncoder()::encodeToString)
        .toList()
        
var decodedNames = encodedNames.stream()
        .map(decoder::decode) // .map(Base64.getDecoder()::decode)
        .map(String::new)
        .toList()
