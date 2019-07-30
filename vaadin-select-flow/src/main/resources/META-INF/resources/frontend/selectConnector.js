// Error handling functions
const tryCatchWrapper = function(originalFunction) {
    return function() {
        try {
            const result = originalFunction.apply(this, arguments);
            return result;
        } catch (error) {
            logError(error.message);
        }
    }
}

function logError(message) {
    console.error("There seems to be an error in the Select:\n" + message + "\n" +
       "Please submit an issue to https://github.com/vaadin/vaadin-select-flow/issues/new!");
}

window.Vaadin.Flow.selectConnector = {
initLazy: tryCatchWrapper(function (select) {
    const _findListBoxElement = tryCatchWrapper(function() {
        for (let i = 0; i < select.childElementCount; i++) {
            const child = select.children[i];
            if ("VAADIN-LIST-BOX" === child.tagName.toUpperCase()) {
                return child;
            }
        }
    });

      // do not init this connector twice for the given select
      if (select.$connector) {
          return;
      }

      select.$connector = {};

      select.renderer = tryCatchWrapper(function(root) {
          const listBox = _findListBoxElement();
          if (listBox) {
              if (root.firstChild) {
                  root.firstChild.remove();
              }
              root.appendChild(listBox);
          }
      });
  })
};
