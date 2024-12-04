package me.wertiko.elyPunishment;

public class PunishmentEvent {
    private String event;
    private String action;
    private long id;
    private String type;
    private String uuid;
    private String ip;
    private String reason;
    private String executorUUID;
    private String executorName;
    private String removedByUUID;
    private String removedByName;
    private String removalReason;
    private long dateStart;
    private long dateEnd;
    private String serverScope;
    private String serverOrigin;
    private boolean silent;
    private boolean ipban;
    private boolean active;
    private String randomID;

    public PunishmentEvent(String event, String action, long id, String type, String uuid, String ip, String reason,
                           String executorUUID, String executorName, String removedByUUID, String removedByName,
                           String removalReason, long dateStart, long dateEnd, String serverScope, String serverOrigin,
                           boolean silent, boolean ipban, boolean active, String randomID) {
        this.event = event;
        this.action = action;
        this.id = id;
        this.type = type;
        this.uuid = uuid;
        this.ip = ip;
        this.reason = reason;
        this.executorUUID = executorUUID;
        this.executorName = executorName;
        this.removedByUUID = removedByUUID;
        this.removedByName = removedByName;
        this.removalReason = removalReason;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.serverScope = serverScope;
        this.serverOrigin = serverOrigin;
        this.silent = silent;
        this.ipban = ipban;
        this.active = active;
        this.randomID = randomID;
    }
}
