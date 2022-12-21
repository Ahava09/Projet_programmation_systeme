cls && javac -d . model/*.java
javac -d . communication/*.java
javac -d . ui/*.java
javac RunClient.java
java RunClient