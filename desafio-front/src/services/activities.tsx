import { fetchData } from "./api/requestHandler"

type ActivityInformation = {
	projectId: number;
	name: string;
	startDate: Date;
	endDate: Date;
};

type ActivityUpdateInformation = {
	projectId: number;
  	id: string;
	name: string;
	startDate: Date;
	endDate: Date;
};

export async function createActivity(projectInformation: ActivityInformation) {
	return await fetchData('/activities', projectInformation, 'POST')
}

export async function updateActivity(id: string, projectInformation: ActivityUpdateInformation) {
	return await fetchData(`/activities/${id}`, projectInformation, 'PUT')
}

export async function getActivities(id: string) {
	return await fetchData(`/activities/${id}`, null, 'GET')
}

export async function setCheckedActivity(id: string) {
	return await fetchData(`/activities/check/${id}`, null, 'GET')
}

export async function deleteActivity(id: string) {
	return await fetchData(`/activities/${id}`, null, 'DELETE')
}