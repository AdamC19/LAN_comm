# LAN Comm

## Overview

This software is designed to allow a remote user (receiver) to receive data from an embedded system. RunServer module runs on a computer connected to an embedded device (in this case, connected on a USB port) from which it reads data. It then relays this data to a client connected to its Network Socket. The receiver is this client. The receiver runs the Receiver module, which will send a BEGIN command to the server, at which point it begins it's read-and-relay routine.