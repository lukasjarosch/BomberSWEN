package hrw.swenpr.bomberman.common.rfc;

/**
 * Defines all types of the RFC.
 * 
 * @author Marco Egger
 */
public enum MessageType {
	LOGIN,
	LOGIN_OK,
	USER,
	LEVEL_SELECTION,
	TIME_SELECTION,
	USER_READY,
	GAME_START,
	ROUND_START,
	ROUND_FINISHED,
	GAME_OVER,
	USER_POSITION,
	USER_DEAD,
	BOMB,
	OBJECT_DESTROYED,
	ERROR_MESSAGE;
}
