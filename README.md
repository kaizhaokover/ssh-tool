#### Command4j

Command executor for Java .  It support two type of connector.
- Runtime  
  Based on Java ProcessBuilder and Process classes
- SSH  
  Based on [JSCH project](http://www.jcraft.com/jsch/).
  It will cache the SSH connection 

#### Maven import
In progress
#### Basic Conception
##### Command  
   All command is subclass of AbstractCommand
  - Console command  
  - Shell command
  - Upload content command
  - Upload file command
  - Upload stream command
  - Download file command
##### Connector
  - Runtime connector  
  
    Annotated by ConnectorLoginMapping(RuntimeLoginInformation.class)
  - SSH connector 
   
  Annotated by ConnectorLoginMapping(SSHLoginInformation.class)
##### LoginInformation
  - Runtime login information
  - SSH login information
##### Handler
  - Running handler  
  
    **CommandExecutionFinishedHandler**
    
  - Finished handler  
  
    **CommandExecutionRunningHandler**
    
#### Usage
   - CommandExecutor.execute  
   
   It will create a connector by LoginInformation type.
   
   When RuntimeLoginInformation is gaven, It will create a RuntimeConnector.
   
   When SSHLoginInformation is gaven , It will create a SSHConnector.
