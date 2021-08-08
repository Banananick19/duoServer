package banana.duo.tasks;

import banana.duo.Server.Server;
import banana.duo.common.Message;
import banana.duo.common.MessageType;

import java.util.*;


public class TaskManager implements Observer {
    private int lastId = 1;
    private HashMap<Integer, Task> tasks;//id->object
    private List<MessageType> runTasks;
    private Server server;


    public TaskManager(Server server) {
        this.runTasks = new ArrayList<MessageType>();
        tasks = new HashMap<Integer, Task>();
        this.server = server;
    }


    public int createNewTask(MessageType type) {
        Task task = Task.task(type);
        if (!runTasks.contains(type)) {
            runTasks.add(type);
        }
        tasks.put(++lastId, task);
        server.addObserver(task);
        return lastId;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Get message in TastManager");
        MessageType type = Message.parseString((String) arg).getMessageType();
        if (!runTasks.contains(type)) {
            createNewTask(type);
        }
    }
}
