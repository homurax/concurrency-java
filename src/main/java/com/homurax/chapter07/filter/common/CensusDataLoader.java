package com.homurax.chapter07.filter.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CensusDataLoader {

	public static CensusData[] load(Path path) {

		List<CensusData> list = new ArrayList<>();

		try (InputStream in = Files.newInputStream(path);
				BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

			String line;
			while ((line = reader.readLine()) != null) {
				CensusData item = processItem(line);
				list.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		CensusData[] result = new CensusData[list.size()];
		return list.toArray(result);

	}

	private static CensusData processItem(String line) {
		
		CensusData censusData = new CensusData();
		String[] tokens = line.split(",");

		censusData.setAge(Integer.valueOf(tokens[0].trim()));
		censusData.setClassOfWorker(tokens[1].trim());
		censusData.setIndustryCode(Integer.valueOf(tokens[2].trim()));
		censusData.setOccupationCode(Integer.valueOf(tokens[3].trim()));
		censusData.setEducation(tokens[4].trim());
		censusData.setWagePerHour(Integer.valueOf(tokens[5].trim()));
		censusData.setEnrolled(tokens[6].trim());
		censusData.setMaritalStatus(tokens[7].trim());
		censusData.setMajorIndustryCode(tokens[8].trim());
		censusData.setMajorOccupationCode(tokens[9].trim());
		censusData.setRace(tokens[10].trim());
		censusData.setHispanicOrigin(tokens[11].trim());
		censusData.setSex(tokens[12].trim());
		censusData.setMemberOfLaborUnion(tokens[13].trim());
		censusData.setReasonForUnemployment(tokens[14].trim());
		censusData.setTimeEmploymentStat(tokens[15].trim());
		censusData.setCapitalGains(Integer.valueOf(tokens[16].trim()));
		censusData.setCapitalLosses(Integer.valueOf(tokens[17].trim()));
		censusData.setDividendsFromStocks(Integer.valueOf(tokens[18].trim()));
		censusData.setTaxFilerStatus(tokens[19].trim());
		censusData.setRegionOfPreviousResidence(tokens[20].trim());
		censusData.setStateOfPreviousResidence(tokens[21].trim());
		censusData.setDetailedHousehold(tokens[22].trim());
		censusData.setDetailedHouseholdSummary(tokens[24].trim());
		censusData.setMigrationCodeChange(tokens[25].trim());
		censusData.setMigrationCodeChangeInReg(tokens[26].trim());
		censusData.setMigrationCodeMoveWithinReg(tokens[27].trim());
		censusData.setLiveInThisHouse(tokens[28].trim());
		censusData.setMigrationPrev(tokens[29].trim());
		censusData.setNumPersonsWorked(tokens[30].trim());
		censusData.setFamilyMembersUnder18(tokens[31].trim());
		censusData.setCountryOfBirthFather(tokens[32].trim());
		censusData.setCountryOfBirthMother(tokens[33].trim());
		censusData.setCountryOfBirthSelf(tokens[34].trim());
		censusData.setCitizenship(tokens[35].trim());
		censusData.setOwnBusiness(tokens[36].trim());
		censusData.setFillIncQuestionnaire(tokens[37].trim());
		censusData.setVeteransBenefits(tokens[38].trim());
		censusData.setWeeksWorkedInYear(Integer.valueOf(tokens[39].trim()));
		censusData.setYear(tokens[40].trim());

		return censusData;
	}

}
