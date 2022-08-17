/**
 * This constant file is used for development environment
 * Summary: All constant values
 */


export const regExp = {
	emailRegEx: /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/, // valid email id
	passwordRegEx: /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[-_\.]).{8,}$/, // allow a-z,A-Z,0-9 and .-_ only
	numberRegEx: /^[0-9]+$/, // number 0-9 only
	urlRgeEx: /^(?:(?:(?:https?|ftp):)?\/\/)(?:\S+(?::\S*)?@)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)(?:\.(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)*(?:\.(?:[a-z\u00a1-\uffff]{2,})))(?::\d{2,5})?(?:[/?#]\S*)?$/, // valid URL
	numSingle: /\d/,
	alphaCap: /[A-Z]/,
	alphaSmall: /[a-z]/,
	alphaSpecial: /[$&+,:;=?@#|'<>.^*()%!-]/
}
export const apiLoader = true;
export const toasterSetting: object = {
	closeButton: true,
	positionClass: "toast-top-right", //top-right,top-left,top-center,bottom-right,bottom-left,bottom-center,top-full-width,bottom-full-width
	timeOut: "5000000000",
	//easing: 'ease-in',
	//easeTime: 300,
	enableHtml: false,
	//progressBar: true,
	//progressAnimation: 'decreasing', //decreasing|increasing
	toastClass: 'ngx-toastr',
	titleClass: 'toast-title',
	messageClass: 'toast-message',
	//tapToDismiss: true,
}