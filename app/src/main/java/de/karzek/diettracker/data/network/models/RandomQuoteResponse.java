package de.karzek.diettracker.data.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Value;

/**
 * Created by MarjanaKarzek on 25.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 25.04.2018
 */

@Value
public class RandomQuoteResponse {

    @SerializedName("quoteText")
    @Expose
    public String quoteText;

    @SerializedName("quoteAuthor")
    @Expose
    public String quoteAuthor;

    @SerializedName("senderName")
    @Expose
    public String senderName;

    @SerializedName("senderLink")
    @Expose
    public String senderLink;

    @SerializedName("quoteLink")
    @Expose
    public String quoteLink;

}
