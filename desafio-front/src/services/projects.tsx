import { fetchData } from "./api/requestHandler"

type ProjectInformation = {
	name: string;
	startDate: Date;
	endDate: Date;
};

type ProjectUpdateInformation = {
  id: string
	name: string;
	startDate: Date;
	endDate: Date;
};

export async function createProject(projectInformation: ProjectInformation) {
	return await fetchData('/projects', projectInformation, 'POST')
}

export async function updateProject(id: string, projectInformation: ProjectUpdateInformation) {
	return await fetchData(`/projects/${id}`, projectInformation, 'PUT')
}

export async function getProjects() {
	return await fetchData('/projects', null, 'GET')
}

export async function deleteProject(id: string) {
	return await fetchData(`/projects/${id}`, null, 'DELETE')
}