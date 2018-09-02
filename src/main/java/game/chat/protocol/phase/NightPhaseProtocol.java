package game.chat.protocol.phase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import game.chat.protocol.Protocol;
import lombok.Setter;

@Setter
public class NightPhaseProtocol implements Protocol {
	
	private String phase; // 어떤 phase에서 보낸 메시지인지
	
	// MafiaVote -> Night : 투표가 유효하지 않아서 MafiaVote -> Plead가 아닌 Night로 이동한 경우 false, 그 외에는 true
	// 과반수가 투표를 하지 않은 경우 | 동률인 경우
	private boolean isValidMafiaVote = true;
	
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
