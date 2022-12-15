# SimpleWebScraper
A project that searches articles online and orders them based on the keywords manually inserted 

Features developed up until now:

## Simple Web Scraping
- Search by main param ( mandatory )
- Search recursive text to find additional parameters
- Save and Load custom additional params
- Selectable amount of pages to analize 
- Selectable amount of pages to save

- Filter for finding only PDF related articles
- Filter for finding articles NOT containing main param
- Selectable separated directory and filename save location
  
- Results ordered by a point system
- Generating a simple html local page version for consultation

## Dblp
- Search by article theme ( mandatory ) 
- declare the year of publication for filtering the results ( optional )
- Selectable amount of research paper to analize ( optional ) ( default is 20 )
- Saving single page of result for later personal note addition in the "Extra" section

## Crossref
- Search by doi ( mandatory )
- showing the result of Crossref form result ( raw html format )

## Internal DB
this program, once started, will generate a folder into your C://Download named "SimpleWebScraperDB"
Here you will find some sub folder that will hold the internal db of all your research and eventually
hold the pdf you may decide to download. 