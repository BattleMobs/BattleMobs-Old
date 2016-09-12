package game.util;

import org.bukkit.entity.Player;

public class PlayerTask {
	
	private Player p;
	private int[] tasks = new int[5];
	
	public PlayerTask(Player p) {
		this.p = p;
	}
	
	public Player getPlayer() {
		return p;
	}
	
	public void addTask(int id, int task) {
		tasks[id] = task;
	}
	
	public void cancelTasks(int id) {
		Scheduler.cancel(tasks[id]);
	}
	
}
