Feature: Check the First Story of The Guardian

Scenario Outline:
Given Open Browser as <browser>
And Load Guardian Website as <webSite>
And Click Top Headline News
Then Top Headline news should open
Given Go to Google
Then Search the Top Headline news from Guardian
And Look for other website article with same article title
Then Print Guardian News is Valid

Examples:
|browser|webSite|
|'chrome'|'https://www.theguardian.com/tone/news'|