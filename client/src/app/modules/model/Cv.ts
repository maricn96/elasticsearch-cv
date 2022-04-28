export class CV {

  id: string;
  applicantName: string;
  applicantSurname: string;
  qualificationLevel: number;
  content: string;
  coverLetter: string;

  constructor(id: string, applicantName: string, applicantSurname: string,
    qualificationLevel: number, content: string, coverLetter: string) {
    this.id = id;
    this.applicantName = applicantName;
    this.applicantSurname = applicantSurname;
    this.qualificationLevel = qualificationLevel;
    this.content = content;
    this.coverLetter = coverLetter;
    }

}
