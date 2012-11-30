hydra-one-click
===============

A simple Hydra setup package

Run start.sh/start.bat to launch the Hydra pipeline and dependencies.

Go to http://localhost:9090/hydra for the admin service REST endpoints:

/hydra/stages  -  GET the configured stages currently running

/hydra/documents/new  -  POST a new JSON document, see examples/documents


Processed documents will end up in ./data