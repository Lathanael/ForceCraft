package de.Lathanael.TheLivingForce.Enums;

public enum Ranks {
	STUDENT(1),
	APPRENTICE(2),
	ACOLYTE(3),
	MAURAUDER(4),
	LORD(5),
	INITIATE(1),
	PADAWAN(2),
	KNIGHT(3),
	GUARDIAN(4),
	MASTER(5),
	FORCE_SENSITIVE(0),
	NONE(-1);

	private Ranks(int rank) {
		this.rank = rank;
	}

	private int rank;

	public int getRank() {
		return rank;
	}
}
