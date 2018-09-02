package game.chat.protocol.phase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import game.chat.protocol.Protocol;
import lombok.Setter;

@Setter
public class MorningPhaseProtocol implements Protocol {
	
	// 어떤 phase에서 보낸 메시지인지
	private String phase;

	// 어떤 그룹이 이겼는지
	// (CIVIL|MAFIA):String, :null
	// null : 아직 이긴 그룹이 없는 경우
	private String winnerGroup;
	
	// 마피아가 Night Phase에서 지목한 유저가 의사에 의해 살아났는지
	// (true|false):boolean
	private boolean isHealed;
	
	// 마피아가 Night Phase에서 살해하려고 지목한 유저
	// UserId:String, null
	// null : 아무도 지목하지 않은 경우
	private String killTaggeduserId; 
	
	// 기자가 Night Phase에서 취재하려고 지목한 유저
	// UserId:String, null
	// null : 아무도 지목하지 않은 경우	
	private String reportTaggedUserId;
	
	// 기자가 Night Phase에서 취재하려고 지목한 유저의 직업명
	// JobName:String
	private String reportTaggedUserJobName;
	
	public MorningPhaseProtocol() {
		this.isHealed = false;
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
