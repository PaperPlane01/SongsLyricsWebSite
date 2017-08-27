PageGlobals = {
    songs : [],
    currentLocale : "",
    keys : [],
    labels : [],

    getLabel : function(key) {
      var label;
      if ($.inArray(key, this.keys) >= 1) {
          for (var index = 0; index < this.labels.length; index++) {
            if (this.labels[index].key == key) {
              label = this.labels[index];
            }
          }
        } else {
          var labelContent = getLabel(key, this.currentLocale);
          label = new Label(key, labelContent);
          this.keys.push(key);
          this.labels.push(label);
        }

        return label.content;
    }
};

Label = function(key, content) {
    this.key = key;
    this.content = content;
};