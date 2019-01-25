package game.chat.protocol.timer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import game.chat.protocol.Protocol;
import lombok.Getter;

public class TimerProtocol implements Protocol {
	private final String protocolType = "TIMER"; 

	@Getter
	private int remainTime;
	
	public TimerProtocol(int maintainTime) {
		this.remainTime = maintainTime;
	}
	
	public Protocol processTime() {
		remainTime--;
		return this;
	}
	
	@Override
	public String toJSONString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return e.toString();
		}
	}

}
