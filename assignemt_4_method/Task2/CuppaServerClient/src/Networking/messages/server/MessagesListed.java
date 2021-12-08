package Networking.messages.server;

import Networking.messages.Message;

import java.util.List;



    /**
     * Message sent to a client listing all the customers that the server
     * has defined.
     */
    public class MessagesListed extends Message {
        private static final long serialVersionUID = 1045L;

        public List<String> chatsNames;


        @Override
        public String toString() {
            return String.format("AllChatsListed(%s)",chatsNames.toString());
        }
}
