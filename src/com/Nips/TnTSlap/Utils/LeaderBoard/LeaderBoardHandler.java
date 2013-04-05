package com.Nips.TnTSlap.Utils.LeaderBoard;


public class LeaderBoardHandler {
	//
	// public static ArrayList<Player> FirstPlace = new ArrayList<Player>();
	// public static ArrayList<Player> SecondPlace = new ArrayList<Player>();
	// public static ArrayList<Player> ThirdPlace = new ArrayList<Player>();
	//
	// public static void getStats() {
	// int am = GameData.PlayersInGame.size();
	// int amount = GameData.TotalKills.size();
	// for (int i = 0; i < am; i++) {
	// int topKills1 = 0;
	// int topKills2 = 0;
	// int topKills3 = 0;
	// Player player = GameData.PlayersInGame.get(i);
	// if (GameData.TotalKills.containsKey(player)) {
	// int kills = GameData.TotalKills.get(player);
	// if (kills > topKills3) {
	// if (kills > topKills2) {
	// if (kills > topKills1) {
	// if (FirstPlace.get(0) != null) {
	// Player first = FirstPlace.get(0);
	// LeaderBoard.sBoard.removeItem(first.getName());
	// if (SecondPlace.get(0) != null) {
	// Player second = SecondPlace.get(0);
	// LeaderBoard.sBoard.removeItem(second.getName());
	// }
	// if (ThirdPlace.get(0) != null) {
	// Player third = ThirdPlace.get(0);
	// LeaderBoard.sBoard.removeItem(third.getName());
	// }
	// }
	// FirstPlace.remove(0);
	// FirstPlace.add(player);
	// topKills1 = kills;
	// Player second = SecondPlace.get(0);
	// Player third = ThirdPlace.get(0);
	// LeaderBoard.sBoard.setItem(player.getName(), kills);
	// LeaderBoard.sBoard.setItem(second.getName(), GameData.TotalKills.get(second));
	// LeaderBoard.sBoard.setItem(third.getName(), GameData.TotalKills.get(third));
	//
	// } else if (kills <= topKills1) {
	// if (SecondPlace.get(0) != null) {
	// Player second = SecondPlace.get(0);
	// LeaderBoard.sBoard.removeItem(second.getName());
	// if (ThirdPlace.get(0) != null) {
	// Player third = ThirdPlace.get(0);
	// LeaderBoard.sBoard.removeItem(third.getName());
	// }
	// }
	// SecondPlace.remove(0);
	// SecondPlace.add(player);
	// topKills2 = kills;
	// Player third = ThirdPlace.get(0);
	// LeaderBoard.sBoard.setItem(player.getName(), kills);
	// LeaderBoard.sBoard.setItem(third.getName(), GameData.TotalKills.get(third));
	// }
	// } else if (kills <= topKills2) {
	// if (ThirdPlace.get(0) != null) {
	// Player third = ThirdPlace.get(0);
	// LeaderBoard.sBoard.removeItem(third.getName());
	// }
	// ThirdPlace.remove(0);
	// ThirdPlace.add(player);
	// topKills3 = kills;
	// LeaderBoard.sBoard.setItem(player.getName(), kills);
	// }
	// }
	// }
	// }
	// }
}
