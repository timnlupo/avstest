/**
 * Copyright 2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Amazon Software License (the "License"). You may not use this file
 * except in compliance with the License. A copy of the License is located at
 *
 * http://aws.amazon.com/asl/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.amazon.alexa.avs.ui;

import com.amazon.alexa.avs.message.response.templateruntime.CardHandler;
import com.amazon.alexa.avs.message.response.templateruntime.RenderTemplate;
import com.amazon.alexa.avs.unity.UnityInterface;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CardView extends JPanel implements CardHandler, SpeechStateChangeListener {

    private static final int CARD_WIDTH = 400;
    private static final int CARD_HEIGHT = 300;
    private static final String CARD_PANEL_NAME = "cardpanel";
    private static final Logger log = LoggerFactory.getLogger(CardView.class);

    private static final UnityInterface unity = new UnityInterface();

    private CardPanel cardPanel;

    public CardView() {
        super();
        this.cardPanel = new CardPanel();
        JScrollPane cardContainer = new JScrollPane(this.cardPanel);
        cardContainer.setBorder(new LineBorder(Color.BLACK));
        cardContainer.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
        this.add(cardContainer);
        this.cardPanel.setName(CARD_PANEL_NAME);
    }

    @Override
    public void onProcessing() {
      unity.updateOnProcessing();
    }

    @Override
    public void onListening() {
      unity.updateOnListening();
      SwingUtilities.invokeLater(() -> cardPanel.clearCard());
    }

    @Override
    public void onProcessingFinished() {
      unity.updateOnProcessingFinished();
    }

    @Override
    public void renderCard(RenderTemplate payload, String rawMessage) {
        SwingUtilities.invokeLater(() -> cardPanel.generateCard(payload, rawMessage));
    }

    @Override
    public void renderPlayerInfo(String rawMessage) {
        SwingUtilities.invokeLater(() -> cardPanel.generatePlayerInfo(rawMessage));
    }

}
