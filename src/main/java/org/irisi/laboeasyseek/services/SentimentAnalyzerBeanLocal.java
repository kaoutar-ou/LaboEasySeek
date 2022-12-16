package org.irisi.laboeasyseek.services;

import org.irisi.laboeasyseek.models.Sentiment;

import jakarta.ejb.Local;

@Local
public interface SentimentAnalyzerBeanLocal {

    Sentiment findSentiment(final String text);

}
