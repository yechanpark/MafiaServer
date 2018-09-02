package game.chat.protocol.phase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import game.chat.protocol.Protocol;
import lombok.Setter;

@Setter
public class PleadPhaseProtocol implements Protocol {
	
	private String phase; // 어떤 phase에서 보낸 메시지인지
	private String pleadUserId; // 어떤 유저가  최후의 변론을 하는지
	
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
