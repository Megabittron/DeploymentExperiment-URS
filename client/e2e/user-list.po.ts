import {browser, element, by, promise} from 'protractor';
import {Key} from 'selenium-webdriver';

export class UserPage {
    static navigateTo(): promise.Promise<any> {
        return browser.get('/users');
    }

    static typeAName(name: string) {
        const input = element(by.id('userName'));
        input.click();
        input.sendKeys(name);
    }

    static selectUpKey() {
        browser.actions().sendKeys(Key.ARROW_UP).perform();
    }

    static backspace() {
        browser.actions().sendKeys(Key.BACK_SPACE).perform();
    }

    static getCompany(company: string) {
        const input = element(by.id('userCompany'));
        input.click();
        input.sendKeys(company);
        const selectButton = element(by.id('submit'));
        selectButton.click();
    }

    static getUserByAge() {
        const input = element(by.id('userName'));
        input.click();
        input.sendKeys(Key.TAB);
    }

    static getUsers() {
        return element.all(by.className('users'));
    }

    static clickClearCompanySearch() {
        const input = element(by.id('companyClearSearch'));
        input.click();
    }

    // http://www.assertselenium.com/protractor/highlight-elements-during-your-protractor-test-run/
    highlightElement(byObject) {
        function setStyle(element, style) {
            const previous = element.getAttribute('style');
            element.setAttribute('style', style);
            setTimeout(() => {
                element.setAttribute('style', previous);
            }, 200);
            return 'highlighted';
        }

        return browser.executeScript(setStyle, element(byObject).getWebElement(), 'color: red; background-color: yellow;');
    }

    getUserTitle() {
        const title = element(by.id('user-list-title')).getText();
        this.highlightElement(by.id('user-list-title'));

        return title;
    }


    getUniqueUser(email: string) {
        const user = element(by.id(email)).getText();
        this.highlightElement(by.id(email));

        return user;
    }

    buttonExists(): promise.Promise<boolean> {
        this.highlightElement(by.id('addNewUser'));
        return element(by.id('addNewUser')).isPresent();
    }

    clickAddUserButton(): promise.Promise<void> {
        this.highlightElement(by.id('addNewUser'));
        return element(by.id('addNewUser')).click();
    }

}
