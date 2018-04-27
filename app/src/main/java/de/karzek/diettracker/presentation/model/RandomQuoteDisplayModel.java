package de.karzek.diettracker.presentation.model;

import lombok.Value;

/**
 * Created by MarjanaKarzek on 25.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 25.04.2018
 */

@Value
public class RandomQuoteDisplayModel {

    public String quoteText;
    public String quoteAuthor;
    public String senderName;
    public String senderLink;
    public String quoteLink;

}
