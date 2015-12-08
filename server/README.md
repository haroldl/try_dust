An Experiment
-

This is a sample web server to see if slow jQuery loading will impact the correctness of the code.

To run the server:

    ./gradlew run

Then browse to http://localhost:5432/try_dust.html to see it work. You can use "inspect element" / "network" to see that the `jquery.min.js` file is pending for 5 seconds and then loads successfully. Then the Dust template is rendered successfully. Notice that the request for the `intro.tl` template is not requested until after jQuery has loaded.
