package Ahyoon.hellospring.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import Ahyoon.hellospring.domain.Member;

public class JdbcTemplateMemberRepository implements MemberRepository{

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcTemplateMemberRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Member save(Member member) {

		// 쿼리를 짤 필요가 없다.
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);	// jdbcTemplate를 넣어서 SimpleJdbcInsert를 만든다.
		jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", member.getName());

		Number key = jdbcInsert.executeAndReturnKey(new	MapSqlParameterSource(parameters));
		member.setId(key.longValue());
		return member;
	}

	@Override
	public Optional<Member> findById(Long id) {
		List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(),id);
		return result.stream().findAny();
	}

	@Override
	public Optional<Member> findByName(String name) {
		List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
		return result.stream().findAny();
	}


	@Override
	public List<Member> findAll() {
		return jdbcTemplate.query("select * from member", memberRowMapper());
	}

	private RowMapper<Member> memberRowMapper(){	// 이 함수는 객체를 생성해주는 역할을 한다.
		return (rs, rowNum) -> {	// 람다함수로 바꾸었다.
			Member member = new Member();
			member.setId(rs.getLong("id"));
			member.setName(rs.getString("name"));
			return member;
		};
	}
}
