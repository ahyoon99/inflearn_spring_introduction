package Ahyoon.hellospring.repository;

import org.springframework.jdbc.datasource.DataSourceUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import Ahyoon.hellospring.domain.Member;

public class JdbcMemberRepository implements MemberRepository{

	private final DataSource dataSource;	// DB에 붙으려면 dataSource라는게 필요하다.

	public JdbcMemberRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Member save(Member member) {

		String sql = "insert into member(name) values(?)";	// 변수보다는 밖에다 상수로 빼는게 낫다.

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	// ResultSet은 결과를 받는 것이다.

		try {
			conn = getConnection();	// DB connection을 가지고 온다.
			pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);	// sql 문장을 작성한다. RETURN_GENERATED_KEYS은 id값을 얻을 때 사용한다.

			pstmt.setString(1, member.getName());	// sql에 있는 ?에 파라미터로 넘어오는 member의 name을 적어준다.

			pstmt.executeUpdate();	// DB에 쿼리가 날라간다.
			rs = pstmt.getGeneratedKeys();	// 38번줄의 RETURN_GENERATED_KEYS와 연관되어 사용된다. DB가 id가 1인 걸 생성했으면 1번을 반환해주고 2인걸 생성했으면 2번를 반환해준다.

			if (rs.next()) {	// rs.next()에 값이 있으면
				member.setId(rs.getLong(1));	// 값을 꺼내어서(get) 세팅(set)해준다.
			} else {
				throw new SQLException("id 조회 실패");
			}
			return member;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			close(conn, pstmt, rs);	// 사용한 자원들을 모두 release 해주어야 한다. 꼭 해주어야 한다!!!
		}
	}

	@Override
	public Optional<Member> findById(Long id) {

		String sql = "select * from member where id = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, id);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				Member member = new Member();
				member.setId(rs.getLong("id"));
				member.setName(rs.getString("name"));
				return Optional.of(member);
			} else {
				return Optional.empty();
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			close(conn, pstmt, rs);
		}
	}

	@Override
	public Optional<Member> findByName(String name) {

		String sql = "select * from member where name = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				Member member = new Member();
				member.setId(rs.getLong("id"));
				member.setName(rs.getString("name"));
				return Optional.of(member);
			}
			return Optional.empty();
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			close(conn, pstmt, rs);
		}
	}

	@Override
	public List<Member> findAll() {

		String sql = "select * from member";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			List<Member> members = new ArrayList<>();
			while(rs.next()) {
				Member member = new Member();
				member.setId(rs.getLong("id"));
				member.setName(rs.getString("name"));
				members.add(member);
			}
			return members;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			close(conn, pstmt, rs);
		}
	}

	private Connection getConnection() {
		return DataSourceUtils.getConnection(dataSource);
	}
	private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
	{
		// close를 역순으로 해준다. -> 매우 복잡..
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (conn != null) {
				close(conn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void close(Connection conn) throws SQLException {
		DataSourceUtils.releaseConnection(conn, dataSource);
	}
}