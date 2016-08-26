package com.paypal.musictag.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.paypal.musictag.values.StaticValues;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-mvc.xml" })
public class TestUuidTypeHandler {
	UuidTypeHandler uuidTypeHandler = new UuidTypeHandler();

	@Test
	public void testGetResult() throws SQLException {

		ResultSet mockedRs = mock(ResultSet.class);

		when(mockedRs.getString("dummyCol")).thenReturn(StaticValues.artistGid0);
		when(mockedRs.getString(0)).thenReturn(StaticValues.artistGid0);

		CallableStatement mockedCs = mock(CallableStatement.class);
		when(mockedCs.getString(0)).thenReturn(StaticValues.artistGid0);
		
		assertEquals(uuidTypeHandler.getResult(mockedRs, 0), UUID.fromString(StaticValues.artistGid0));
		assertEquals(uuidTypeHandler.getResult(mockedRs, "dummyCol"), UUID.fromString(StaticValues.artistGid0));
		
		assertEquals(uuidTypeHandler.getResult(mockedCs, 0), UUID.fromString(StaticValues.artistGid0));
	}
}
