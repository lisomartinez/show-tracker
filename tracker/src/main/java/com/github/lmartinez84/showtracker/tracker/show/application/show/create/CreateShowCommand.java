package com.github.lmartinez84.showtracker.tracker.show.application.show.create;

import com.github.lmartinez84.showtracker.shared.domain.bus.command.Command;

public final class CreateShowCommand implements Command {
    private final String id;
    private final String title;
    private final int year;

    public CreateShowCommand(String id, String title, int year) {
        this.id = id;
        this.title = title;
        this.year = year;
    }

    public String id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CreateShowCommand that = (CreateShowCommand) o;

        if (year != that.year) {
            return false;
        }
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        return title != null ? title.equals(that.title) : that.title == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + year;
        return result;
    }

    @Override
    public String toString() {
        return "CreateShowCommand{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", year=" + year +
                '}';
    }

    public String title() {
        return title;
    }

    public int year() {
        return year;
    }
}
