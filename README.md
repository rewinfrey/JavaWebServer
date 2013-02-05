#Java Web Server

This is a simple Java Web Server built lovingly from scratch as part of my 8th Light Apprenticeship.

##Installation & Running

`$ git glone git@github.com:rewinfrey/JavaWebServer.git`

`$ cd JavaWebServer`

`$ ant all`

`$ java -jar out/artifacts/RickHttpServer_jar/RickHttpServer.jar -p 3000 -d /directory/you/want/served`

###Usage & Predefined Routes

The Java Server currently serves the following file types:

.html
.pdf
.png / .jpg / .gif / .ico
.txt / .rb / .java / .php


The following predefined routes are also available:

/      : takes you to the base directory (supplied when starting the server)

/hello : serves a friendly hello page

/time  : serves the current system time

/form  : serves an http form

###License

Copyright (c) 2013 Rick Winfrey

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.