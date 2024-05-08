package using_spring;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public record Fields(
        String id,
        String subject,
        String note,
        String amount,
        String fragment) 
{
    public Fields(
            @Value("${column.id}") String id,
            @Value("${column.subject}") String subject,
            @Value("${column.note}") String note,
            @Value("${extra.amount}") String amount,
            @Value("${fragment.pseudo}") String fragment)
    {
        this.id = id;
        this.subject = subject;
        this.note = note;
        this.amount = amount;
        this.fragment = fragment;
    }
}