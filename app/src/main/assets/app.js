const resumeEN = {
  photo: "",
  firstName: "",
  lastName: "",
  jobTitle: "",
  city: "",
  postalCode: "",
  country: "",
  phone: "",
  email: "",
  education: [
    {
      school: "",
      degree: "",
      graduationDate: "",
      description: "",
    }
  ],
  links: [
    {
      label: "GitHub Profile",
      link: "https://github.com/ucdevinda123",
    }
  ],
  skills: [
    "Java",
    "Android",
    "JavaScript",
    "TypeScript",
    "Angular",
    "AWS",
    "Devops",
    "AWS Services",
    "PostgreSQL",
    "Oracle-PL SQL",
    "MySQL",
    "Game developtment",
    "Advanced analytical thinking",
    "Adaptability",
  ],
  languages: ["English ", "Japanese"],
  professionalSummary: "",
  employmentHistory: [
    {
      jobTitle: "",
      startDate: "",
      endDate: "",
      employer: "",
      city: "",
      achievements: [""],
    },
  ],
};

const lang = "EN";

const formatResume = (r, l) => ({
  resume: {
    ...r,
    address: [r.country, r.city, r.postalCode].filter(Boolean).join(", "),
    lang: l,
  },
});

const resumeApp = Vue.createApp({
  data() {
    return {
      resume: {
        ...resumeEN,
        address: [resumeEN.country, resumeEN.city, resumeEN.postalCode]
          .filter(Boolean)
          .join(", "),
        lang: lang,
      },
    };
  },
  methods: {
    changeLang: function (lang) {
      this.resume = resumeEN;
    },

    renderResumeDocument: function (jsonData) {
         console.log("______");
         console.log(jsonData);
         this.resume = jsonData
          console.log(this.resume);
    },
  },
});

const mobileAppProxyObj = resumeApp.mount("#app");