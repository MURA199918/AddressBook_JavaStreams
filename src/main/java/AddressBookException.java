public class AddressBookException extends Exception{

    enum ExceptionType{
        CONNECTION_PROBLEM, RETRIEVAL_PROBLEM;
    }

    ExceptionType type;

    AddressBookException(String message, ExceptionType type){
        super(message);
        this.type = type;
    }
}
