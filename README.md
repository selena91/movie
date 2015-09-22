# movie

Project is developed in the LightTable IDE and Clojure programming language.
NetFlix Prize dataset is used to test and implement algorithm for movies recommendation.

### CONTENT
Dataset is customized and data is splited to 3 tables:
table customers [CustomerID, Password] - used for customer login to application
table movies [MovieID, Title, YearOfRelease] - collection of all movies
table ratings [MovieID, CustomerID, Rating, Date] - contains rates that customers gave to certain movies
All tables' columns are strings, except column Rating which is a number between 1 and 5, and Date which is date.

The purpose is to recommend to logged customer ten new movies (for example to watch and rate) based on his past rates.
Firstly, similarity with other customers is determined by calculation of Jaccard similarity coefficient (https://en.wikipedia.org/wiki/Jaccard_index).
Secondly, top ten movies rated with the highest rates by ten most similar customers are those recommended ones.

The database used in project is SQLLite database located in file movie.rar.

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

## License

Copyright © 2015 FIXME
